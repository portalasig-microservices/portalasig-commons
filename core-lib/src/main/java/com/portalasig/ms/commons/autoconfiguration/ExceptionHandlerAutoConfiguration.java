package com.portalasig.ms.commons.autoconfiguration;

import com.portalasig.ms.commons.configuration.exception.ApiExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Autoconfiguration for registering the {@link ApiExceptionHandler} in web applications.
 * <p>
 * This configuration is only enabled when the application is a web application.
 * It imports the {@link ApiExceptionHandler} to handle API exceptions globally.
 */
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@Import({
        ApiExceptionHandler.class
})
public class ExceptionHandlerAutoConfiguration {
}
