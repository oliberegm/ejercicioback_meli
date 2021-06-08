package com.cloud.meli.trace.config;

import springfox.documentation.service.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String TAG_TRACEINFO = "TRACEINFO";
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.cloud.meli.trace.controller"))
                .build()
                .tags(
                        new Tag(TAG_TRACEINFO, "Esta Api Rest se utiliza para obtener informacion de ips y estadisticas")
                );

    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Service Authentication")
                .version("1.0")
                .license("Apache License Version 2.0")
                .termsOfServiceUrl("https://olibersystem.com.ar/apis/tc.html")
                .description("Descripcion API para mercadolibre")
                .contact(new Contact("Oliber Garcia", "https://olibersystem.com.ar", "oliber.system@gmail.com"))
                .build();
    }
}
