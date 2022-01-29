package com.oclothes.global.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String API_NAME = "오늘의 옷 API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "오늘의 옷 API 명세서";

    @Bean
    public Docket apiV1() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter parameter = parameterBuilder.name("X-ACCESS-TOKEN")
                .description("X-ACCESS-TOKEN")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(parameter);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("groupName1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.oclothes.domain"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
}
