// package com.base.common.config;

// import java.util.Collections;

// import org.springdoc.core.configuration.SpringDocConfiguration;
// import org.springdoc.core.models.GroupedOpenApi;
// import org.springframework.boot.autoconfigure.AutoConfigureBefore;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.ExternalDocumentation;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.media.StringSchema;
// import io.swagger.v3.oas.models.parameters.Parameter;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;

// /**
// * SpringDoc API文档相关配置
// * Created by macro on 2022/3/4.
// */
// @Configuration
// @AutoConfigureBefore(SpringDocConfiguration.class)
// public class SpringDocConfig {
// // @Bean
// // public OpenAPI myOpenAPI() {
// // return new OpenAPI()
// // .info(new Info().title("State Eval API")
// // .description("State Eval API")
// // .version("v1.0.0")
// // .license(new License().name("Apache 2.0")))
// // // .url("https://github.com/macrozheng/mall-learning")))
// // .externalDocs(new ExternalDocumentation()
// // .description("State Eval文档"));
// // // .url("http://www.macrozheng.com"));
// // }

// // @Bean
// // public GroupedOpenApi adminApi() {
// // return GroupedOpenApi.builder()
// // .group("admin")
// // .packagesToScan("com.base")
// // .pathsToMatch("/api/v1/**")
// // .build();
// // }

// // @Bean
// // public GroupedOpenApi evalApi() {
// // return GroupedOpenApi.builder()
// // .group("eval")
// // .packagesToScan("com.eval")
// // .pathsToMatch("/api/eval/**")
// // .build();
// // }

// private static final String TOKEN_HEADER = "Authorization";

// @Bean
// public OpenAPI openApi() {
// // 针对 knife4j（增强UI），这里添加全局请求头（addParameters）无效，只能按组添加，待官方解决
// return new OpenAPI()
// .components(
// new Components().addSecuritySchemes(TOKEN_HEADER,
// new SecurityScheme()
// .type(SecurityScheme.Type.APIKEY)
// // 这里配置 bearer 后，你的请求里会自动在 token 前加上
// // Bearer
// .scheme("bearer")
// .bearerFormat("JWT"))
// .addParameters(TOKEN_HEADER,
// new Parameter()
// .in("header")
// .schema(new StringSchema())
// .name(TOKEN_HEADER)))
// .info(new Info().title("文档标题")
// .description("文档描述")
// .contact(new Contact().name("作者").email("邮箱")
// .url("可以写你的博客地址或不填"))
// // 参考 Apache 2.0 许可及地址，你可以不配此项
// .license(new License().name("Apache 2.0")
// .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
// .version("0.1"))
// // 引入外部的文档，我这里引得是 springdoc 官方文档地址，你可以不配此项
// .externalDocs(new ExternalDocumentation()
// .description("SpringDoc Full Documentation")
// .url("https://springdoc.org/"));
// }

// /**
// * GroupedOpenApi 是对接口文档分组，类似于 swagger 的 Docket
// *
// * @return
// */
// @Bean
// public GroupedOpenApi authApi() {
// return GroupedOpenApi.builder()
// // 组名
// .group("eval API")
// // 扫描的路径，支持通配符
// .pathsToMatch("/api/eval/**")
// // 扫描的包
// .packagesToScan("com.eval")
// .build();
// }

// @Bean
// public GroupedOpenApi sysApi() {
// return GroupedOpenApi.builder()
// .group("admin API")
// .pathsToMatch("/api/v1/**")
// // 添加自定义配置，这里添加了一个用户认证的 header，否则 knife4j 里会没有 header
// .addOperationCustomizer((operation, handlerMethod) -> operation.security(
// Collections.singletonList(
// new SecurityRequirement().addList(TOKEN_HEADER))))
// .build();
// }

// }