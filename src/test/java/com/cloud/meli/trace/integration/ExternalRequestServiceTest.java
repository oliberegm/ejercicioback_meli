package com.cloud.meli.trace.integration;

import com.cloud.meli.trace.ChallengeMeliApplication;
import com.cloud.meli.trace.dtos.CountryIpDTO;
import com.cloud.meli.trace.exception.CountriUrlException;
import com.cloud.meli.trace.exception.SearchCountryServiceExpection;
import com.cloud.meli.trace.model.Country;
import com.cloud.meli.trace.repository.CountryRepository;
import com.cloud.meli.trace.repository.StatisticsRepository;
import com.cloud.meli.trace.service.impl.ExternalRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeMeliApplication.class})
@Slf4j
public class ExternalRequestServiceTest {
    @Value("${api.ip.url}")
    private String ipSearchUrl;
    @Value("${api.restcountries.name.url}")
    private String countrySearchUrl;
    @Value("api.fixer.url")
    private String currencyUrl;
    @Value("api.fixer.code")
    private String currencyCode;
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ExternalRequestsService externalRequestsService;
    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private StatisticsRepository statisticsRepository;

    @Test
    public void searchIpTest() throws CountriUrlException {
        //String json = "[{\"name\":\"Spain\",\"topLevelDomain\":[\".es\"],\"alpha2Code\":\"ES\",\"alpha3Code\":\"ESP\",\"callingCodes\":[\"34\"],\"capital\":\"Madrid\",\"altSpellings\":[\"ES\",\"Kingdom of Spain\",\"Reino de España\"],\"region\":\"Europe\",\"subregion\":\"Southern Europe\",\"population\":46438422,\"latlng\":[40.0,-4.0],\"demonym\":\"Spanish\",\"area\":505992.0,\"gini\":34.7,\"timezones\":[\"UTC\",\"UTC+01:00\"],\"borders\":[\"AND\",\"FRA\",\"GIB\",\"PRT\",\"MAR\"],\"nativeName\":\"España\",\"numericCode\":\"724\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}],\"translations\":{\"de\":\"Spanien\",\"es\":\"España\",\"fr\":\"Espagne\",\"ja\":\"スペイン\",\"it\":\"Spagna\",\"br\":\"Espanha\",\"pt\":\"Espanha\",\"nl\":\"Spanje\",\"hr\":\"Španjolska\",\"fa\":\"اسپانیا\"},\"flag\":\"https://restcountries.eu/data/esp.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"ESP\"}]";
        String ip = "1.1.1.1";
        log.info(ipSearchUrl);
        Mockito
                .when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.<Class<CountryIpDTO>>any()))
                .thenReturn(new ResponseEntity<CountryIpDTO>(CountryIpDTO.builder().countryName("Spain").build(), HttpStatus.OK));

        Assertions.assertEquals("Spain", externalRequestsService.searchIp(ip).getCountryName());
    }
    @Test
    public void searchCountryTest() throws SearchCountryServiceExpection {
        String country = "Germany";
        Mockito
                .when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<String>(ValuesJson.COUNTRY_INFO_JSON, HttpStatus.OK));
        Assertions.assertEquals(country, externalRequestsService.searchCountry(country).getName());
    }

    @Test
    public void searchCurrencyTest() throws CountriUrlException {
        String currencyCode = "ARS";
        Mockito
                .when( restTemplate.exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(ValuesJson.CURRENCY_INFO_JSON, HttpStatus.OK));
        externalRequestsService.searchCurrency();
    }

}
