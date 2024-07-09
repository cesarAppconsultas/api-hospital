package com.springboot.hospital.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "componentsCRUD",
                version = "1.0",
                description = "primera version"
        )

)
public class OpenApiConfig {
}
