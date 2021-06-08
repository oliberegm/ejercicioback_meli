package com.cloud.meli.trace.unit;

import com.cloud.meli.trace.ChallengeMeliApplication;
import com.cloud.meli.trace.converts.CountryConvert;
import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.datafixer.CurrencyDTO;
import com.cloud.meli.trace.dtos.restcountries.CountryDTO;
import com.cloud.meli.trace.dtos.restcountries.Currency;
import com.cloud.meli.trace.dtos.restcountries.Language;
import com.cloud.meli.trace.dtos.restcountries.Translation;
import com.cloud.meli.trace.exception.CountryJsonParseException;
import com.cloud.meli.trace.integration.ValuesJson;
import com.cloud.meli.trace.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChallengeMeliApplication.class})
@Slf4j
public class ConvertUnitTest {
    @Autowired
    private CountryConvert countryConvert;


    @Test
    public void convertJson() throws CountryJsonParseException, JsonProcessingException {
        String json =  "[{\"name\":\"Spain\",\"topLevelDomain\":[\".es\"],\"alpha2Code\":\"ES\",\"alpha3Code\":\"ESP\",\"callingCodes\":[\"34\"],\"capital\":\"Madrid\",\"altSpellings\":[\"ES\",\"Kingdom of Spain\",\"Reino de España\"],\"region\":\"Europe\",\"subregion\":\"Southern Europe\",\"population\":46438422,\"latlng\":[40.0,-4.0],\"demonym\":\"Spanish\",\"area\":505992.0,\"gini\":34.7,\"timezones\":[\"UTC\",\"UTC+01:00\"],\"borders\":[\"AND\",\"FRA\",\"GIB\",\"PRT\",\"MAR\"],\"nativeName\":\"España\",\"numericCode\":\"724\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}],\"translations\":{\"de\":\"Spanien\",\"es\":\"España\",\"fr\":\"Espagne\",\"ja\":\"スペイン\",\"it\":\"Spagna\",\"br\":\"Espanha\",\"pt\":\"Espanha\",\"nl\":\"Spanje\",\"hr\":\"Španjolska\",\"fa\":\"اسپانیا\"},\"flag\":\"https://restcountries.eu/data/esp.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"ESP\"}]";
        String finalJsonError =  "[\"name\":\"Spain\",\"topLevelDomain\":[\".es\"],\"alpha2Code\":\"ES\",\"alpha3Code\":\"ESP\",\"callingCodes\":[\"34\"],\"capital\":\"Madrid\",\"altSpellings\":[\"ES\",\"Kingdom of Spain\",\"Reino de España\"],\"region\":\"Europe\",\"subregion\":\"Southern Europe\",\"population\":46438422,\"latlng\":[40.0,-4.0],\"demonym\":\"Spanish\",\"area\":505992.0,\"gini\":34.7,\"timezones\":[\"UTC\",\"UTC+01:00\"],\"borders\":[\"AND\",\"FRA\",\"GIB\",\"PRT\",\"MAR\"],\"nativeName\":\"España\",\"numericCode\":\"724\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}],\"translations\":{\"de\":\"Spanien\",\"es\":\"España\",\"fr\":\"Espagne\",\"ja\":\"スペイン\",\"it\":\"Spagna\",\"br\":\"Espanha\",\"pt\":\"Espanha\",\"nl\":\"Spanje\",\"hr\":\"Španjolska\",\"fa\":\"اسپانیا\"},\"flag\":\"https://restcountries.eu/data/esp.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"ESP\"}]";

        ArrayList<CountryDTO> countryDTOArrayList = countryConvert.listCountryDTOToJson(json);
        Assertions.assertNotNull(countryDTOArrayList);
        Assertions.assertNotNull(countryDTOArrayList.get(0));
        Assertions.assertEquals("Spain", countryDTOArrayList.get(0).getName());

        Country c = countryConvert.countryFromCountryDTO(
                CountryDTO.builder()
                        .name("Spain")
                        .languages(new ArrayList<>(Arrays.asList(new Language("es", "ESP", "España", ""))))
                        .translations(new ArrayList<>((Arrays.asList(new Translation("es", "España")))))
                        .latlng(new ArrayList<>(Arrays.asList(10.0, 10.0)))
                        .currencies(new ArrayList<>(Arrays.asList(new Currency("ESP", "ESPAÑA", "ESP"))))
                        .build());
        Assertions.assertNotNull(c);
        Assertions.assertEquals("Spain", c.getName());

        IpInfoDTO infoDTO = countryConvert.ipInfoDTOFromCountry(c);
        Assertions.assertNotNull(infoDTO);
        Assertions.assertEquals("Spain", infoDTO.getName());

        CurrencyDTO currencyDTO = countryConvert.currencyDTOToJson(ValuesJson.CURRENCY_INFO_JSON);
        Assertions.assertNotNull(currencyDTO);
        Assertions.assertTrue(currencyDTO.getRates().size() > 1);
    }
}
