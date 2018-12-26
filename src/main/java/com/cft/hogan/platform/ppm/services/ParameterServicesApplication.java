package com.cft.hogan.platform.ppm.services;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.cft.hogan.platform.ppm.services.context.SystemContext;
import com.cft.hogan.platform.ppm.services.massmaintenance.util.Utils;

//Declare Application class
@SpringBootApplication

//Scan components using annotations
@ComponentScan(basePackages="com.cft.hogan.platform.ppm.services")
//
//enable JPA Repositories
//@EnableJpaRepositories(basePackages="com.cft.hogan.platform.ppm.services.massmaintenance")
//
////Scan JPA entities
//@EntityScan(basePackages="com.cft.hogan.platform.ppm.services.massmaintenance")

//Disable Auto Config RoutingDataSource & DataSourceTransactionManager
@EnableAutoConfiguration(exclude = { //
     DataSourceAutoConfiguration.class, //
     DataSourceTransactionManagerAutoConfiguration.class,
     HibernateJpaAutoConfiguration.class })
public class ParameterServicesApplication {

	public static void main(String[] args) {
		//load the property context
		try {
			SystemContext.loadPropertyContext(args);
			SpringApplication.run(ParameterServicesApplication.class, args);
			SystemContext.logDetails();
		} catch (IOException e) {
			Utils.handleException(e);
		}
	}
}
