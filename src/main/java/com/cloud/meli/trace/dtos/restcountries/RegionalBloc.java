package com.cloud.meli.trace.dtos.restcountries;

import com.cloud.meli.trace.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegionalBloc  implements DTO {
    private String acronym;
    private String name;
    private ArrayList<String> otherAcronyms;
    private ArrayList<String> otherNames;
}
