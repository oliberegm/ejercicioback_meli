package com.cloud.meli.trace.dtos.restcountries;

import com.cloud.meli.trace.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Currency implements DTO {
    private String code;
    private String name;
    private String symbol;
}
