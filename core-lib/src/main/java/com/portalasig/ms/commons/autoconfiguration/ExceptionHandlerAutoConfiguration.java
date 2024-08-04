package com.portalasig.ms.commons.autoconfiguration;

import com.portalasig.ms.commons.configuration.exception.ApiExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Autoconfigures exception handlers to customize HTTP response.
 */
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@Import({
        ApiExceptionHandler.class
})
public class ExceptionHandlerAutoConfiguration {
}
