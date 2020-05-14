package edu.miu.cs544.medappointment.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/v1/*";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(metadata())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.miu.cs544.medappointment.ui.controller.api"))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo metadata(){
        return new ApiInfoBuilder()
                .title("Meditation Checking Appointment System API")
                .description("We are trying to create a RESTful web services application (you only need to write the backend part of it;\n" +
                        "no UI is necessary) to automate the process of meditation checking appointment system where students\n" +
                        "can create, update, view and cancel (CRUD operations) their appointment reservations and TM checkers\n" +
                        "can create, update, view and delete appointments. Admins have full access. In general, checkers create\n" +
                        "appointments and students can make reservations for those appointments (please note the difference\n" +
                        "between appointment and reservation); then the checkers must go back and accept or decline those\n" +
                        "reservations. Students/checkers will receive an email once an appointment has been accepted/declined. ")
                .version("1.0")
                .license("(C) Copyright Team #2")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}
