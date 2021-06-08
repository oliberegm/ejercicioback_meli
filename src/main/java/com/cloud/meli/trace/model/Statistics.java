package com.cloud.meli.trace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    @Id
    private String id;
    private String name;
    private String code3;
    private String translate;
    private Double distance;
    private Long invocations;
}
