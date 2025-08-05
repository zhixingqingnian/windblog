package com.zhixing.weblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4j 文档配置
 */
// 定义配置类
@Configuration
// 自动生成API文档
@EnableSwagger2WebMvc
// 只在dev环境中开启
@Profile("dev")
public class Knife4jAdminConfig {
    /**
     * 创建 Api 文档
     *
     * @return
     */
    @Bean("adminApi")
    public Docket createApiDoc() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .groupName("admin 后台接口")
                .select()
                // 指定controller的扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.zhixing.weblog.admin.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 构建 Api 信息
     *
     * @return
     */
    public ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("后台接口文档")
                .description("前后端分离博客")
                .termsOfServiceUrl("https://www.windblog.website")
                .contact(new Contact("知性", "https://www.windblog.website", "15956682104@163.com"))
                .version("1.0")
                .build();
    }
}
