package com.cloud.meli.trace.controller;

import com.cloud.meli.trace.config.SwaggerConfig;
import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.StatsDTO;
import com.cloud.meli.trace.dtos.TraceDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;



@Api(tags = {SwaggerConfig.TAG_TRACEINFO})
public interface ApiTraceController {

    @PostMapping("/trace")
    @ApiOperation(value="Obtener informacion de la ip ingresada",
            notes = "Retorna informacion del pais al que pertenece la ip",
            tags = SwaggerConfig.TAG_TRACEINFO)
    ResponseEntity<IpInfoDTO> trace(
            @ApiParam(value = "Ip valida para obtener info", required = true, example = "1.1.1.1", type= "String" )
            @RequestBody IpDTO ip);

    @GetMapping("/stats")
    @ApiOperation(value="Retorna un top 10 de los paises consultados",
            notes = "Listado top 10",
            tags = SwaggerConfig.TAG_TRACEINFO)
    ResponseEntity<List<StatsDTO>> stats();
}
