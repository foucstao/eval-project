package com.base.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;

/**
 * spring doc配置
 */
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI springShopOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("态势评估系统")
                                                .description("态势评估系统API文档")
                                                .version("v3"));
        }

        // @Bean
        // public OpenAPI springShopOpenAPI() {
        // return new OpenAPI()
        // .components(new Components()
        // // // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer
        // .addSecuritySchemes("bearer-key",
        // new SecurityScheme().type(SecurityScheme.Type.HTTP)
        // .scheme("bearer")
        // .bearerFormat("JWT")))
        // .schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme())
        // .addSecurityItem(new
        // SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
        // .info(new Info().title("态势评估系统")
        // .description("态势评估系统API文档")
        // .version("v3")
        // .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        // .externalDocs(new ExternalDocumentation()
        // .description("外部文档")
        // .url("https://springshop.wiki.github.org/docs"));
        // }

        // @Bean
        // public OpenAPI customOpenAPI() {
        // Contact contact = new Contact();
        // contact.setName("eebd");

        // OpenAPI openAPI = new OpenAPI().info(new Info().title("Title"));
        // // oauth2.0 password
        // openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
        // // 全局安全校验项，也可以在对应的controller上加注解SecurityRequirement
        // openAPI.addSecurityItem(new
        // SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));

        // return openAPI;
        // }

        // private SecurityScheme securityScheme() {
        // SecurityScheme securityScheme = new SecurityScheme();
        // // 类型
        // securityScheme.setType(SecurityScheme.Type.APIKEY);
        // // 请求头的name
        // securityScheme.setName(HttpHeaders.AUTHORIZATION);
        // // token所在未知
        // securityScheme.setIn(SecurityScheme.In.HEADER);
        // return securityScheme;
        // }

        // @Bean
        // public GroupedOpenApi actuatorApi() {
        // return GroupedOpenApi.builder().group("Actuator")
        // .pathsToMatch("/v3/**")
        // .build();
        // }

        // @Bean
        // public GroupedOpenApi evalGroup(@Value("${springdoc.version}") String
        // appVersion) {
        // return GroupedOpenApi.builder().group("evals")
        // .addOperationCustomizer((operation, handlerMethod) -> {
        // operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
        // return operation;
        // })
        // .addOpenApiCustomizer(openApi -> openApi
        // .info(new Info().title("Eval API").version(appVersion)))
        // .packagesToScan("com.eval")
        // .build();
        // }

        // private static final String SECURITY_SCHEME_NAME = "Bearer access_token";

        // @Bean
        // public OpenAPI mallTinyOpenAPI() {
        // return new OpenAPI()
        // .info(new Info().title("态势评估系统")
        // .description("态势评估系统 API")
        // .version("v1.0.0")
        // .license(new License().name("Apache 2.0")
        // .url("https://github.com/macrozheng/mall-learning")))
        // // .externalDocs(new ExternalDocumentation()
        // // .description("SpringBoot实战电商项目mall（50K+Star）全套文档")
        // // .url("http://www.macrozheng.com"))
        // .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
        // .components(new Components()
        // .addSecuritySchemes(SECURITY_SCHEME_NAME,
        // new SecurityScheme()
        // .name(SECURITY_SCHEME_NAME)
        // .type(SecurityScheme.Type.HTTP)
        // .in(SecurityScheme.In.HEADER)
        // .scheme("bearer")
        // .bearerFormat("JWT")));
        // }

        private static final String BEARER_AUTH = "bearerAuth";

        @Bean
        public GroupedOpenApi backendGroup() {
                return GroupedOpenApi.builder().group("eval").displayName("Eval API")
                                .addOpenApiCustomizer(openApi -> openApi
                                                .info(new Info().title("EVAL API").version("1.0.0")))
                                .packagesToScan("com.eval")
                                .pathsToMatch("/api/eval/**")
                                .addOpenApiCustomizer(openApi -> openApi.components(new Components()
                                                .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP).scheme("bearer")
                                                                .bearerFormat("JWT"))))
                                .addOperationCustomizer((operation, handlerMethod) -> {
                                        operation.addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
                                        return operation;
                                })
                                .build();
        }

}