package com.challenge.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.challenge.school.controller"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("School Data Management","CRUD app for school data"))
                .apiInfo(getAppInfo())
                .useDefaultResponseMessages(false);
    }

    public ApiInfo getAppInfo(){
        return new ApiInfo(
                "School API",
                "Students, Subjects, Groups and Teachers management",
                "0.0.1",
                "NULL",
                new Contact("Juan Granados","URL","jpablo@mobiquityinc.com"),
                "LICENCE",
                "Licence URL",
                Collections.emptyList()
        );
    }
}
