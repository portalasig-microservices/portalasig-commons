package com.portalasig.ms.commons.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

/**
 * Spring configuration for customizing Jackson's ObjectMapper.
 * <p>
 * This configuration:
 * <ul>
 *   <li>Enables case-insensitive property mapping.</li>
 *   <li>Fails on unknown and ignored properties during deserialization.</li>
 *   <li>Sets a custom date format: yyyy-MM-dd'T'HH:mm:ss.SSSZ.</li>
 *   <li>Enables pretty printing of JSON output.</li>
 *   <li>Uses snake_case for property names.</li>
 *   <li>Excludes null values from serialization.</li>
 * </ul>
 */
@Configuration
public class JacksonSerializationConfiguration {

    /**
     * Configures the Jackson2ObjectMapperBuilder with custom settings.
     *
     * @return a customized Jackson2ObjectMapperBuilder bean
     */
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToEnable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }
}