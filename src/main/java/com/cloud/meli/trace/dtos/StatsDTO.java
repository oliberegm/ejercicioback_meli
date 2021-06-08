package com.cloud.meli.trace.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatsDTO {
    private String country;
    private String distance;
    private String invocation;
}
