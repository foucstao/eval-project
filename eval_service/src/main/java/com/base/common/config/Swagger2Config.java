// package com.base.common.config;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.service.AuthorizationScope;
// import springfox.documentation.service.GrantType;
// import springfox.documentation.service.OAuth;
// import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
// import springfox.documentation.service.SecurityReference;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spi.service.contexts.SecurityContext;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger.web.DocExpansion;
// import springfox.documentation.swagger.web.ModelRendering;
// import springfox.documentation.swagger.web.OperationsSorter;
// import springfox.documentation.swagger.web.SecurityConfiguration;
// import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
// import springfox.documentation.swagger.web.TagsSorter;
// import springfox.documentation.swagger.web.UiConfiguration;
// import springfox.documentation.swagger.web.UiConfigurationBuilder;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Configuration
// @EnableSwagger2
// public class Swagger2Config {

// @Value("${server.port}")
// private String port;

// @Value("${swagger.enabled}")
// Boolean swaggerEnabled;

// @Bean
// public Docket createRestApi() {
//         // @formatter:off
// 		return new Docket(DocumentationType.SWAGGER_2)
// 				.securitySchemes(Arrays.asList(securitySchema()))
// 				.apiInfo(apiInfo())
// 				.enable(swaggerEnabled)
// 				.select()
// 				// .apis(RequestHandlerSelectors.basePackage("com.smartvideo.controller"))
//                 .apis(RequestHandlerSelectors.basePackage("com.rbac.controller"))
// 				.paths(PathSelectors.any())
// 				.build()
// 				.securityContexts(Collections.singletonList(securityContext()));
// 		// @formatter:on
// }

// private OAuth securitySchema() {
// String accessTokenUri = "http://localhost:" + port + "/oauth/token";
// List<AuthorizationScope> authorizationScopeList = new
// ArrayList<AuthorizationScope>();
// authorizationScopeList.add(new AuthorizationScope("read", "read all"));
// authorizationScopeList.add(new AuthorizationScope("write", "access all"));
// List<GrantType> grantTypes = new ArrayList<GrantType>();
// GrantType passwordCredentialsGrant = new
// ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
// grantTypes.add(passwordCredentialsGrant);

// return new OAuth("oauth2", authorizationScopeList, grantTypes);
// }

// private SecurityContext securityContext() {
// return SecurityContext.builder().securityReferences(defaultAuth()).build();
// }

// private List<SecurityReference> defaultAuth() {

// final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
// authorizationScopes[0] = new AuthorizationScope("read", "read all");
// authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
// authorizationScopes[2] = new AuthorizationScope("write", "write all");

// return Collections.singletonList(new SecurityReference("oauth2",
// authorizationScopes));
// }

// @Bean
// SecurityConfiguration security() {
//         // @formatter:off
// 		return SecurityConfigurationBuilder
// 				.builder()
// 				.clientId("browser")
// 				.clientSecret("123456")
// 				.realm("test-app-realm")
// 				.appName("test-app")
// 				.scopeSeparator(",")
// 				.additionalQueryStringParams(null)
// 				.useBasicAuthenticationWithAccessCodeGrant(false).build();
// 		// @formatter:on
// }

// @Bean
// UiConfiguration uiConfig() {
//         // @formatter:off
// 		return UiConfigurationBuilder
// 				.builder()
// 				.deepLinking(true)
// 				.displayOperationId(false)
// 				.defaultModelsExpandDepth(1)
// 				.defaultModelExpandDepth(1)
// 				.defaultModelRendering(ModelRendering.EXAMPLE)
// 				.displayRequestDuration(false)
// 				.docExpansion(DocExpansion.NONE)
// 				.filter(false)
// 				.maxDisplayedTags(null)
// 				.operationsSorter(OperationsSorter.ALPHA)
// 				.showExtensions(false)
// 				.tagsSorter(TagsSorter.ALPHA)
// 				.validatorUrl(null).build();
// 		// @formatter:on
// }

// private ApiInfo apiInfo() {
//         // @formatter:off
// 		return new ApiInfoBuilder()
// 				.title("SmartVideo RestfulAPI接口文档")
// 				.description("欢迎访问后端API接口文档")
// 				.version("1.0")
// 				.build();
// 		// @formatter:on
// }
// }
