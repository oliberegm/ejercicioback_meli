package com.cloud.meli.trace.dtos.datafixer;

import com.cloud.meli.trace.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO implements DTO {
    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private LinkedHashMap<String, Double> rates;
}
