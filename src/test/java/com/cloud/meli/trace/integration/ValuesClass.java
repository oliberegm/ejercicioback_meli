package com.cloud.meli.trace.integration;

import com.cloud.meli.trace.dtos.CountryIpDTO;
import com.cloud.meli.trace.dtos.IpDTO;
import com.cloud.meli.trace.dtos.restcountries.CountryDTO;
import com.cloud.meli.trace.dtos.restcountries.Currency;
import com.cloud.meli.trace.dtos.restcountries.Language;
import com.cloud.meli.trace.dtos.restcountries.Translation;
import com.cloud.meli.trace.model.Country;
import com.cloud.meli.trace.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValuesClass {

    public static CountryIpDTO countryIpDTO = CountryIpDTO.builder()
            .countryName("Spain")
            .countryCode3("ESP")
            .countryCode("ES")
            .build();

    public static CountryDTO countryDTO = CountryDTO.builder()
            .name("Spain")
            .alpha2Code("ES")
            .alpha3Code("ESP")
            .currencies(new ArrayList<>(Arrays.asList(new Currency("EUR", "Euro", ""))))
            .latitude(40.0)
            .longitude(-4.0)
            .latlng(new ArrayList<>(Arrays.asList(10.0, 10.0)))
            .currencies(new ArrayList<>(Arrays.asList(new Currency("ESP", "ESPAÑA", "ESP"))))
            .timezones(new ArrayList<>(Arrays.asList("UTC", "UTC+01:00")))
            .languages(new ArrayList<>(Arrays.asList(new Language("es", "spa", "Spanish", "Español"))))
            .translations(new ArrayList<>(Arrays.asList(new Translation("es", "España"))))
            .build();
    public static Country country = Country.builder()
            .code2(countryDTO.getAlpha2Code())
            .code3(countryDTO.getAlpha3Code())
            .isoCode(countryDTO.getLanguages().get(0).getIso639_1())
            .languages(countryDTO.getLanguages().stream().collect(Collectors.toMap(Language::getName, Language::getIso639_1)))
            .name(countryDTO.getName())
            .times(countryDTO.getTimezones())
            .translations(countryDTO.getTranslations().stream().collect(Collectors.toMap(Translation::getLanguage, Translation::getTranslation)))
            .distance(Util.distanceCoord(countryDTO.getLatitude(), countryDTO.getLongitude()))
            .build();;
}
