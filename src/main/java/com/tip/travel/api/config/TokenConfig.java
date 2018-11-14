package com.tip.travel.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    private String secret;
    private String algorithm;
    private Long expiresSecond;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Long getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(Long expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
