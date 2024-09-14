package com.books.libraryapi.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Api")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Marcello")
                                .email("marcellojoaquim1@hotmail.com")
                                .url("https://github.com/marcellojoaquim"))
                        .description("Library api project with Spring Boot")
                        .termsOfService("")
                        .license(new License()
                                .name("Apache 2.0")
                                .url(""))
                );
    }





}
