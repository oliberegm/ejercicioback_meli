package com.cloud.meli.trace.service;

import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.StatsDTO;
import com.cloud.meli.trace.exception.TraceServiceException;

import java.util.List;

public interface ITraceService {

    public IpInfoDTO traceInfo(IpDTO ip) throws TraceServiceException;

    public List<StatsDTO> statsInfo();
}
