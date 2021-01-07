package ru.glowgrew.spit.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;

import java.util.function.Supplier;

/**
 * @author glowgrew
 */
public class AdaptedObjectMapperServiceImpl implements AdaptedObjectMapperService {

    @Override
    public ObjectMapper createAdaptedObjectMapper() {
        return createAdaptedObjectMapper(ObjectMapper::new);
    }

    @Override
    public ObjectMapper createAdaptedObjectMapper(Supplier<? extends ObjectMapper> objectMapperSupplier) {
        final ObjectMapper objectMapper = objectMapperSupplier.get();
        adaptObjectMapper(objectMapper);
        return objectMapper;
    }

    @Override
    public void adaptObjectMapper(ObjectMapper objectMapper) {
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .setDefaultPrettyPrinter(prettyPrinter)
                    .setVisibility(PropertyAccessor.FIELD, Visibility.PUBLIC_ONLY)
                    .setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
                    .setVisibility(PropertyAccessor.IS_GETTER, Visibility.PUBLIC_ONLY)
                    .setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
                    .setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY)
                    .registerModule(new Jdk8Module())
                    .registerModule(new ParanamerModule())
                    .registerModule(new GuavaModule());
    }
}