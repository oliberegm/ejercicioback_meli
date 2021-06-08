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
public class Language  implements DTO {
    private String iso639_1;
    private String iso639_2;
    private String name;
    private String nativeName;

    public String getIso639_1() {
        if(iso639_1 == null) {
            return getIso639_2();
        }
        return iso639_1;
    }

    public String getIso639_2() {
        if(iso639_2 == null) {
            return "";
        }
        return iso639_2;
    }
}
