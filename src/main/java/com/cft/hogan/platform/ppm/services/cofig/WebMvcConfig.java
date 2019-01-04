package com.cft.hogan.platform.ppm.services.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v1/parameter/mass-maintenance/**")
				.allowedOrigins(
						"http://localhost:3000",
						"http://cscvappnor088.fsg.amer.csc.com", 
						"http://cscvappnor090.fsg.amer.csc.com"
						)
				.allowedMethods("*")
				.allowCredentials(true);
			}
		};
	}
}
