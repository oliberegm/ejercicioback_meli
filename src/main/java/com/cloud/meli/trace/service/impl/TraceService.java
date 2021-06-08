package com.cloud.meli.trace.service.impl;

import com.cloud.meli.trace.converts.CountryConvert;
import com.cloud.meli.trace.dtos.CountryIpDTO;
import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.StatsDTO;
import com.cloud.meli.trace.dtos.restcountries.CountryDTO;
import com.cloud.meli.trace.exception.CountriUrlException;
import com.cloud.meli.trace.exception.SearchCountryServiceExpection;
import com.cloud.meli.trace.exception.TraceServiceException;
import com.cloud.meli.trace.model.Country;
import com.cloud.meli.trace.model.Statistics;
import com.cloud.meli.trace.repository.CountryRepository;
import com.cloud.meli.trace.repository.CurrencyRepository;
import com.cloud.meli.trace.repository.StatisticsRepository;
import com.cloud.meli.trace.service.ITraceService;
import com.cloud.meli.trace.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TraceService implements ITraceService {

    private final ExternalRequestsService requestsService;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final CountryConvert countryConvert;
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public TraceService(ExternalRequestsService requestsService, CountryRepository countryRepository,
                        CurrencyRepository currencyRepository, CountryConvert countryConvert,
                        StatisticsRepository statisticsRepository) {
        this.requestsService = requestsService;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.countryConvert = countryConvert;
        this.statisticsRepository = statisticsRepository;
    }


    public Statistics statistics(String code3) {
        Optional<Statistics> statistics = statisticsRepository.findByCode3(code3);
        Statistics st = null;
        st = statistics.get();
        st.setInvocations(st.getInvocations() + 1L );
        statisticsRepository.save(st);
        return st;
    }

    public IpInfoDTO loadCountry(String country, String code3) throws SearchCountryServiceExpection {
        // aqui busco la info del pais
        // si no esta la guardo en la bb
        Country count = null;
        Optional<Country> c = countryRepository.findByCode3(code3);
        if(c.isPresent()) {
            count = c.get();
        } else {
            CountryDTO dto = this.requestsService.searchCountry(code3);
            count = countryConvert.countryFromCountryDTO(dto);
            countryRepository.save(count);
            statisticsRepository.save(new Statistics(null, count.getName(), count.getCode3(), count.getTranslations().get("es"), count.getDistance(), 0L));
        }
        IpInfoDTO ipdto = countryConvert.ipInfoDTOFromCountry(count);
        ipdto.setCurrency(count.getCurrency() + " (1 " + count.getCurrency() + " = " + this.loadCurrency(count.getCurrency()) + " USD)");
        return ipdto;
    }

    private Double loadCurrency(String code) {
        Double dc = currencyRepository.findByCountry(code).isPresent() ?
                currencyRepository.findByCountry(code).get().getExchange() :
                0.0;
        return dc;
    }

    @Override
    public IpInfoDTO traceInfo(IpDTO ip) throws TraceServiceException {
        if(!Util.validateIp(ip.getIp())) {
            throw new TraceServiceException("Ip invalid!!!!");
        }
        try {
            CountryIpDTO countryIpDTO = this.requestsService.searchIp(ip.getIp());
            log.info("data search ip {}", countryIpDTO);

            IpInfoDTO dto =  this.loadCountry(countryIpDTO.getCountryName(), countryIpDTO.getCountryCode3());

            //dto.setCurrency(dc.toString());
            dto.setIp(ip.getIp());
            dto.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            this.statistics(countryIpDTO.getCountryCode3());
            log.debug("info final {}", dto);
            return dto;
        } catch (CountriUrlException | SearchCountryServiceExpection e) {
            log.error("Error service: {}", e.getMessage());
            e.printStackTrace();
            throw new TraceServiceException(e.getMessage());
        }
    }

    @Override
    public List<StatsDTO> statsInfo() {
        return statisticsRepository.findAll().stream()
                .sorted(Comparator.comparing(Statistics::getInvocations).reversed())
                .map(m -> StatsDTO.builder().distance(m.getDistance()+" kms").invocation(m.getInvocations().toString()).country(m.getTranslate()).build())
                .collect(Collectors.toList());

    }
}