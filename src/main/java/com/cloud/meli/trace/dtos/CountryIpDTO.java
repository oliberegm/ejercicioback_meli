package com.cloud.meli.trace.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryIpDTO implements DTO{
    private String countryCode;
    private String countryCode3;
    private String countryName;
    private String countryEmoji;
}
