package com.cloud.meli.trace.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpDTO implements DTO{
    @ApiModelProperty(value = "1.1.1.1")
    private String ip;
}
