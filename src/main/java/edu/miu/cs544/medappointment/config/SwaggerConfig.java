package edu.miu.cs544.medappointment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.miu.cs544.medappointment.ui.controller.api"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metadata());
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
}
