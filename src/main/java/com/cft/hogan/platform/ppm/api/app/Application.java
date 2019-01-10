package com.cft.hogan.platform.ppm.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.cft.hogan.platform.ppm.api.config.context.EnvironmentContext;

@SpringBootApplication
@ComponentScan(basePackages="com.cft.hogan.platform.ppm")
@EntityScan(basePackages="com.cft.hogan.platform.ppm")
@EnableAutoConfiguration(exclude = { //
     DataSourceAutoConfiguration.class, //
     DataSourceTransactionManagerAutoConfiguration.class,
     HibernateJpaAutoConfiguration.class })
public class Application extends SpringBootServletInitializer implements ApplicationRunner {
	
	@Autowired
    private Environment env;  
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		
			SpringApplication.run(Application.class, args);
			EnvironmentContext.logDetails();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		 EnvironmentContext.setEnvironment(env);
		
	}
}
