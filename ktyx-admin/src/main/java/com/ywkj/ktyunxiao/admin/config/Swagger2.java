package com.ywkj.ktyunxiao.admin.config;

import com.ywkj.ktyunxiao.common.config.JwtProperties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 8:57
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    private Predicate<RequestHandler> swaggerSelector = RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)::apply;

    @Autowired
    JwtProperties jwtProperties;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name(jwtProperties.getHeader()).description("令牌").defaultValue("Bearer ").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ywkj.ktyunxiao.*.controller"))
                .apis(swaggerSelector::test)
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("客通云销 RESTFul APIs")
                .description("本系统由mgz和lwh研发")
                .contact(new Contact("MiaoGuozhu","http://www.ktyunxiao.com/","summitpointmgz@gmail.com"))
                .version("0.0.1")
                .build();
    }



}

