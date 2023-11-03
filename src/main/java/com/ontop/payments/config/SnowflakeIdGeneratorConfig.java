package com.ontop.payments.config;

import com.ontop.payments.shared.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SnowflakeIdGeneratorConfig {
    @Bean
    public SnowflakeIdGenerator httpClient() {
        return new SnowflakeIdGenerator(1);
    }
}
