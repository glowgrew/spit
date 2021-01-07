package ru.glowgrew.spit.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Supplier;

/**
 * @author glowgrew
 */
public interface AdaptedObjectMapperService {

    ObjectMapper createAdaptedObjectMapper();

    ObjectMapper createAdaptedObjectMapper(Supplier<? extends ObjectMapper> objectMapperSupplier);

    void adaptObjectMapper(ObjectMapper objectMapper);

}