package com.cp2396f09_sem4_grp3.onmart_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "onmart.services")
public record ServiceUrlConfig(
        String order) {
}

