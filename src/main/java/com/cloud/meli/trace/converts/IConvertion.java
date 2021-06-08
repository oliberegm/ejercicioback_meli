package com.cloud.meli.trace.converts;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface  IConvertion <E, D> {

    public D convertToDto(E entity);

    public E convertToEntity(D dto);
}
