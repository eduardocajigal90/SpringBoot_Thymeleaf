package com.quiox.test.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class },scanBasePackages = {"com.quiox"})
@EnableJpaRepositories(basePackages = {"com.quiox"})
@EntityScan(basePackages = {"com.quiox"})
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
