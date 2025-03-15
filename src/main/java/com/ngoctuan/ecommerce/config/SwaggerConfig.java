package com.ngoctuan.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info().title(String.format("%s Application API", appName)).description(
                        String.format("This is a %s using springdoc-openapi and OpenAPI 3. Version 1.0.0", appName)));
    }
}
