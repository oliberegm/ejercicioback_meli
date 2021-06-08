package com.cloud.meli.trace.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;


@Document
@Data
@Builder
public class Country {
    @Id
    private String id;
    private String name;
    private String code2;
    private String code3;
    private String nameTranslate;
    private String isoCode;
    private Double distance;
    // moneda a cambio
    private String currency;
    private Map<String,String> languages;
    private List<String> times;
    private Map<String, String> translations;
    //campo estadistico private Long invocation;

}
