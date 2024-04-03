package com.ping.simpleauthzengine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public AuthorizationEngine authorizationEngine() {
        return new SimpleAuthorizationEngine();
    }
}
