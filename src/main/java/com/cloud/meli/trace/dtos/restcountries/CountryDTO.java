package com.cloud.meli.trace.dtos.restcountries;

import com.cloud.meli.trace.dtos.DTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO implements DTO {
    private String name;
    private ArrayList<String> topLevelDomain;
    private String alpha2Code;
    private String alpha3Code;
    private ArrayList<String> callingCodes;
    private String capital;
    private ArrayList<String> altSpellings;
    private String region;
    private String subregion;
    private int population;
    private ArrayList<Double> latlng;
    private double latitude;
    private double longitude;
    private String demonym;
    private double area;
    private double gini;
    private ArrayList<String> timezones;
    private ArrayList<String> borders;
    private String nativeName;
    private String numericCode;
    private ArrayList<Currency> currencies;
    private ArrayList<Language> languages;
    private ArrayList<Translation> translations;
    private String Flag;
    private ArrayList<RegionalBloc> regionalBlocs;
    private String cioc;
}
