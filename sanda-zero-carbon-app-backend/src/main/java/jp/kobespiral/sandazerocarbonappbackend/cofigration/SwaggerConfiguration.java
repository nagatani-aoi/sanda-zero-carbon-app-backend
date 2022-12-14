package jp.kobespiral.sandazerocarbonappbackend.cofigration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .protocols(Collections.singleton("https")) // <- ポイント 1
                .host("es4.eedept.kobe-u.ac.jp") // <- ポイント 2
                // .protocols(Collections.singleton("http")) // <- ポイント 1
                // .host("localhost:18080") // <- ポイント 2
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sanda Zero Carbon App")
                .description("Sanda Zero Carbon Appのバックエンド")
                .version("0.0.1")
                .contact(new Contact("Aoi Nagatani", "https://es4.eedept.kobe-u.ac.jp",
                        "ing@ws.cs.kobe-u.ac.jp"))
                .license("license")
                .licenseUrl("license URL")
                .termsOfServiceUrl("")
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .build();
    }
}
