package com.cloud.meli.trace.controller;

import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.StatsDTO;
import com.cloud.meli.trace.exception.TraceServiceException;
import com.cloud.meli.trace.service.impl.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TraceController implements ApiTraceController {

    private final TraceService traceService;
    @Autowired
    public TraceController(TraceService traceService) {
        this.traceService = traceService;
    }


    @Override
    public ResponseEntity<IpInfoDTO> trace(IpDTO ip) {
        log.info("init trace {} ", ip);
        IpInfoDTO dto;
        HttpStatus status;
        try {
            dto = traceService.traceInfo(ip);
            status = HttpStatus.OK;
        } catch (TraceServiceException | Exception e) {
            log.error("Internal service {}", e.getMessage());
            dto = new IpInfoDTO();
            dto.setErrorMessage(e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        log.info("finalize trace: {} {}", status, dto);
        return new ResponseEntity(dto, status);
    }

    @Override
    public ResponseEntity<List<StatsDTO>> stats() {
        log.info("init stast");
        List<StatsDTO> listDto;
        HttpStatus status = HttpStatus.OK;
        listDto = traceService.statsInfo();
        log.info("finalize stast {} {}", listDto, status);
        return new ResponseEntity(listDto, status);
    }
}
