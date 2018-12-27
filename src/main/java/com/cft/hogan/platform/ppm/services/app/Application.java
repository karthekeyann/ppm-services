package com.cft.hogan.platform.ppm.services.app;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.cft.hogan.platform.ppm.services.config.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

@SpringBootApplication
@ComponentScan(basePackages="com.cft.hogan.platform.ppm.services")
@EntityScan(basePackages="com.cft.hogan.platform.ppm.services")
@EnableAutoConfiguration(exclude = { //
     DataSourceAutoConfiguration.class, //
     DataSourceTransactionManagerAutoConfiguration.class,
     HibernateJpaAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		try {
			SystemContext.loadPropertyContext(args);
			SpringApplication.run(Application.class, args);
			SystemContext.logDetails();
		} catch (IOException e) {
			Utils.handleException(e);
		}
	}
}
