package com.micasa.config;

import com.micasa.interceptor.LoggingRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class for flatService.
 */
@Configuration
public class FlatServiceWebConfig implements WebMvcConfigurer
{
    /**
     * Adds a new request interceptor.
     * @param registry : interceptorRegistry object.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingRequestInterceptor());
    }
}
