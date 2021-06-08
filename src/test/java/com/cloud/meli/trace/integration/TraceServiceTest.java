package com.cloud.meli.trace.integration;

import com.cloud.meli.trace.ChallengeMeliApplication;
import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.exception.CountriUrlException;
import com.cloud.meli.trace.exception.SearchCountryServiceExpection;
import com.cloud.meli.trace.exception.TraceServiceException;
import com.cloud.meli.trace.service.impl.ExternalRequestsService;
import com.cloud.meli.trace.service.impl.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeMeliApplication.class})
@Slf4j
public class TraceServiceTest {
    @MockBean
    private ExternalRequestsService externalRequestsService;
    @Autowired
    private TraceService traceService;

    @Test
    public void traceInfoTest() throws CountriUrlException, SearchCountryServiceExpection, TraceServiceException {

        Mockito
                .when(externalRequestsService.searchIp(ArgumentMatchers.anyString()))
                .thenReturn(ValuesClass.countryIpDTO);
        Mockito
                .when(externalRequestsService.searchCountry(ArgumentMatchers.anyString()))
                .thenReturn(ValuesClass.countryDTO);
        log.info("{}", traceService.traceInfo(new IpDTO("1.1.1.1")));
        log.info("{}", traceService.statsInfo());

    }

}
