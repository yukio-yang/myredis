package com.yukio.common.config;

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

/**
 * @author yukio
 * @create 2022-02-10 9:15
 * swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		//添加请求参数token
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		ArrayList<Parameter> parameters = new ArrayList<>();
		parameterBuilder.name("token").description("令牌")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		parameters.add(parameterBuilder.build());
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build();
				return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.yukio.abc"))
				.paths(PathSelectors.regex("/abc/.*")).build().globalOperationParameters(parameters);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("测试文档")
				.description("这是一个测试文档")
				.version("1.0")
				.build();
	}
}
