package com.cloud.meli.trace.service.impl;

import com.cloud.meli.trace.converts.CountryConvert;
import com.cloud.meli.trace.dtos.CountryIpDTO;
import com.cloud.meli.trace.dtos.datafixer.CurrencyDTO;
import com.cloud.meli.trace.dtos.restcountries.CountryDTO;
import com.cloud.meli.trace.exception.CountriUrlException;
import com.cloud.meli.trace.exception.CountryJsonParseException;
import com.cloud.meli.trace.exception.SearchCountryServiceExpection;
import com.cloud.meli.trace.model.Currency;
import com.cloud.meli.trace.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExternalRequestsService {
    public static final String SEARCHCOUNTRY_CACHE_KEY = "searchCountry";
    public static final String SEARCHIP_CACHE_KEY = "searchIp";
    public static final String SEARCHCURRENCY_CACHE_KEY = "searchCurrency";

    @Value("${api.restcountries.name.url}")
    private String countriesNameUrl;
    @Value("${api.ip.url}")
    private String ipSearchUrl;
    @Value("${api.fixer.url}")
    private String currencyUrl;
    @Value("${api.fixer.code}")
    private String currencyCode;

    private final RestTemplate restTemplate;
    private final CountryConvert countryConvert;
    private final CurrencyRepository currencyRepository;
    private final CacheManager cacheManager;

    public ExternalRequestsService(RestTemplate restTemplate, CountryConvert countryConvert,
                                   CurrencyRepository currencyRepository, CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.countryConvert = countryConvert;
        this.currencyRepository = currencyRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * Busca los datos del pais en el servicio externo
     * @param country
     * @return
     * @throws SearchCountryServiceExpection
     */
    @Cacheable(SEARCHCOUNTRY_CACHE_KEY)
    public CountryDTO searchCountry(String country) throws SearchCountryServiceExpection {
        try {
            ResponseEntity<String> info = restTemplate
                    .getForEntity(countriesNameUrl + country, String.class);
            log.debug("info country {}", info.getBody() );
            ArrayList<CountryDTO> countryDTO = countryConvert.listCountryDTOToJson(info.getBody());
            log.debug("Element 0 {}", countryDTO.get(0));
            return countryDTO.get(0);
        } catch (CountryJsonParseException e) {
            e.printStackTrace();
            throw new SearchCountryServiceExpection(e.getMessage());
        }
    }

    /**
     *
     * Busca en el servicio externo ip
     * lo cacheamos para mejorar los tiempos de respuesta
     * @param ip
     * @return el codigo del pais
     *
     */
    @Cacheable(SEARCHIP_CACHE_KEY)
    public CountryIpDTO searchIp(String ip) throws CountriUrlException {
        log.info("search ip: {}", ipSearchUrl + ip);
        try {
            ResponseEntity<CountryIpDTO> ipinfo = restTemplate.getForEntity(ipSearchUrl + ip, CountryIpDTO.class);
            if( ipinfo.getStatusCode().is4xxClientError() ) {
                log.info("lanzar error de url no encotrada");
            }
            if( ipinfo.getStatusCode().isError() ) {
                log.info("ha ocurrido in error {}", ipinfo.getStatusCode() );
            }
            log.info("response ipinfo {}", ipinfo);
            return ipinfo.getBody();
        } catch (HttpClientErrorException exception) {
            log.info("hay error {} {}", exception.getMessage(), exception.getStatusCode());
            exception.printStackTrace();
            throw new CountriUrlException(exception.getMessage());
        }
    }
/*
    private Double calculateCurrency(String codeCountry, LinkedHashMap<String, Double> list) {
        String usd = "USD";
        Double v1 = list.get(usd);
        Double v2 = list.get(codeCountry);
        if(codeCountry == "EUR" || v2 == null)
            v2 = 1.0;//por ser EUR
        return v1 / v2;
    }*/
    /**
     * Aqui nos permite cargar la datas del server
     * para todos currency
     */
    @Async("processExecutor")
    @Scheduled(fixedDelay = 1000 * 60 * 60 )
    public void searchCurrency() throws CountriUrlException {
        log.debug("search currency all");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("access_key", currencyCode);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> info = restTemplate.exchange(
                            currencyUrl+currencyCode,
                            HttpMethod.GET,
                            entity,
                            String.class);
            log.debug("Value response {}", info.getBody());
            CurrencyDTO currencyDTO = countryConvert.currencyDTOToJson(info.getBody());
            log.debug("parse currency {}", currencyDTO);
            if( info.getStatusCode().is4xxClientError() ) {
                log.debug("lanzar error de url no encotrada");
            }
            if( info.getStatusCode().isError() ) {
                log.debug("ha ocurrido in error {}", info.getStatusCode() );
            }
            Double usd = currencyDTO.getRates().get("USD");
            List<Currency> listCurrency = currencyRepository.findAll();
            currencyDTO.getRates().entrySet().stream().forEach(
                    f -> {
                        Optional<Currency> oc = listCurrency.stream().filter( filter -> filter.getCountry().equals(f.getKey())).findAny();
                        Currency c = oc.isPresent() ? oc.get() : new Currency("", f.getKey(),  f.getValue());
                        c.setExchange( usd / f.getValue() );
                        currencyRepository.save(c);
                    }
            );
            cacheManager.getCache(SEARCHCURRENCY_CACHE_KEY).clear();
        } catch (HttpClientErrorException | JsonProcessingException exception) {
            exception.printStackTrace();
            log.debug("hay error {} {}", exception.getMessage(), exception.getMessage());
            throw new CountriUrlException(exception.getMessage());
        }
    }

}
