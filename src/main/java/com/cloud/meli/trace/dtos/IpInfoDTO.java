package com.cloud.meli.trace.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IpInfoDTO implements DTO {
    @ApiModelProperty(value = "1.1.1.1")
    private String ip;
    @ApiModelProperty(value = "21/11/2020 15:12:03")
    private String date;
    @ApiModelProperty(value = "España")
    private String name;
    @ApiModelProperty(value = "España")
    private String traduccion;
    @ApiModelProperty(value = "EUR (1 EUR = 1.063 USD")
    private String currency;
    @ApiModelProperty(value = "es")
    private String isoCode;
    @ApiModelProperty(value = "15000 kms")
    private String distance;
    @ApiModelProperty(value = "[\"Español (es)\"]")
    private List<String> languages;
    @ApiModelProperty(value = "(UTC)")
    private List<String> times;
    @ApiModelProperty(value = "Ip invalida")
    private String errorMessage;
}
