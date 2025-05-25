package com.um.pasarela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableZuulProxy
@EnableWebSecurity
public class PasarelaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PasarelaApplication.class, args);
	}
}