package com.cloud.meli.trace.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceDTO implements DTO{
    private String ip;
    private String date;
    private String country;
    private String iso_code;
    private List<String> languages;
    private String currency;
    private List<String> times;
    private String estimated_distance;

}
