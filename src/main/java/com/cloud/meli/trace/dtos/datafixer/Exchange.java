package com.cloud.meli.trace.dtos.datafixer;

import com.cloud.meli.trace.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exchange  implements DTO {
    private String country;
    private Float value;
}
