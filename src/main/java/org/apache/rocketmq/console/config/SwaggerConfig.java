package org.apache.rocketmq.console.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author duanz
 * @date 2019-05-22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Rocketmq-Console")
				.contact(new Contact("段子彧", "duanziyu@xiangshang360.com", "")).version("1.0").build();
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select().apis(RequestHandlerSelectors.basePackage("org.apache.rocketmq.console.controller"))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
	}

}
