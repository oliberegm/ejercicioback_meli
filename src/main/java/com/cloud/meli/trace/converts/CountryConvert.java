package com.cloud.meli.trace.converts;

import com.cloud.meli.trace.dtos.IpInfoDTO;
import com.cloud.meli.trace.dtos.datafixer.CurrencyDTO;
import com.cloud.meli.trace.dtos.restcountries.CountryDTO;
import com.cloud.meli.trace.dtos.restcountries.Language;
import com.cloud.meli.trace.dtos.restcountries.Translation;
import com.cloud.meli.trace.exception.CountryJsonParseException;
import com.cloud.meli.trace.model.Country;
import com.cloud.meli.trace.utils.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CountryConvert{

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final Gson gson;

    @Autowired
    public CountryConvert(ModelMapper modelMapper, ObjectMapper objectMapper, Gson gson) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.gson = gson;
    }

    private CountryDTO parserDTO(JsonElement element) {
        JsonObject e = element.getAsJsonObject().getAsJsonObject("translations");
        Set keys = e.keySet();
        JsonArray array = new JsonArray();
        for (Object key : keys) {
            String value = e.get(key.toString()).toString();
            JsonObject translation = new JsonObject();
            translation.addProperty("translation", value);
            translation.addProperty("language", key.toString());
            array.add(translation);

        }
        JsonObject elem = element.getAsJsonObject();
        elem.remove("translations");
        elem.add("translations", array);
        CountryDTO country = gson.fromJson(elem.toString(), CountryDTO.class);
        return country;
    }

    public ArrayList<CountryDTO> listCountryDTOToJson(String json) throws CountryJsonParseException {
        ArrayList<CountryDTO> list = new ArrayList<>();
        JsonArray jsonelement = JsonParser.parseString(json).getAsJsonArray();
        if (jsonelement.isJsonArray()) {
            for (JsonElement el : jsonelement) {
                list.add(parserDTO(el));
            }
        }
        return list;
    }

    public Country countryFromCountryDTO(CountryDTO countryDTO) {
        return Country.builder()
                .code2(countryDTO.getAlpha2Code())
                .code3(countryDTO.getAlpha3Code())
                .isoCode(countryDTO.getLanguages().get(0).getIso639_1())
                .languages(countryDTO.getLanguages().stream().collect(Collectors.toMap(Language::getName, Language::getIso639_1)))
                .name(countryDTO.getName())
                .times(countryDTO.getTimezones())
                .translations(countryDTO.getTranslations().stream().collect(Collectors.toMap(Translation::getLanguage, Translation::getTranslation)))
                .distance(Util.distanceCoord(countryDTO.getLatlng().get(0), countryDTO.getLatlng().get(1)))
                .currency(countryDTO.getCurrencies().get(0).getCode())
                .build();
    }

    public IpInfoDTO ipInfoDTOFromCountry(Country country){
        return IpInfoDTO.builder()
                .name(country.getName())
                .isoCode(country.getCode2())
                .languages(country.getLanguages().entrySet().stream().map(m -> m.getValue() + "  (" + m.getKey() + ")" ).collect(Collectors.toList())  )
                .traduccion(country.getTranslations().get("es"))
                .times(country.getTimes())
                .distance(country.getDistance() + " kms")
                .build();
    }

    public CurrencyDTO currencyDTOToJson(String json) throws JsonProcessingException {
        CurrencyDTO dto = objectMapper.readValue(json, CurrencyDTO.class);
        return dto;
    }

}
