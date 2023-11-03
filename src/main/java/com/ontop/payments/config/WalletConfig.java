package com.ontop.payments.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "wallet")
public class WalletConfig {
    private String endpoint;

    // Getters y setters

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}