package com.cft.hogan.platform.ppm.api.cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(env.getProperty("spring.cors.mapping"))
				.allowedOrigins(
						env.getProperty("spring.cors.origins1"),
						env.getProperty("spring.cors.origins2"), 
						env.getProperty("spring.cors.origins3")
						)
				.allowedMethods("*")
				.allowCredentials(true);
			}
		};
	}
}
