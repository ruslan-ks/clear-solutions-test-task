package com.rkostiuk.cstask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("valid.user")
public record UserValidationProperties(Integer minAge) {
}
