package com.quiox.test.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi
      .builder()
      .group("")
      .pathsToMatch("com.quiox.test/controller/**")
      .build();
  }

  @Bean
  public OpenAPI springShopOpenAPI() {
    Contact c = new Contact();
    c.setEmail("quiox@quiox.com");
    c.setUrl("https://www.quiox.com/");
    c.setName("Quiox");
    return new OpenAPI()
    .info(
        new Info()
          .title("Test Quiox API")
          .description("Register section and product base project API")
          .version("v1.0")
          .license(
            new License().name("Apache 2.0").url("https://springdoc.org")
          )
          .contact(c)
      );
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper model = new ModelMapper();
    model.getConfiguration().setDeepCopyEnabled(true);
    model.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    model.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return model;
  }
}
