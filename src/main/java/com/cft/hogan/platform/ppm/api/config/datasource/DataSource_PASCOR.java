package com.cft.hogan.platform.ppm.api.config.datasource;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cft.hogan.platform.ppm.api.util.Constants;

@Configuration
@EnableTransactionManagement
public class DataSource_PASCOR extends DataSource_Base{

	@Bean
	public DataSource corDatasource() throws Exception {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(getProperties().getProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(getProperties().getProperty("spring.datasource.url.pascor"));
		dataSource.setUsername(getProperties().getProperty("spring.datasource.username.pascor"));
		dataSource.setPassword(getProperties().getProperty("spring.datasource.password.pascor"));

		return dataSource;
	}

	@Bean(name = "entityManagerFactoryPASCOR")
	public LocalContainerEntityManagerFactoryBean corEntityManager() throws Exception {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(corDatasource());
		em.setPackagesToScan(new String[] { Constants.PACKAGE_ENTITIES});
		em.setPersistenceUnitName(Constants.DATASOURCE_PASCOR); 

		//
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		em.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> properties = new HashMap<>();

		// JPA & Hibernate
		properties.put("hibernate.dialect", getProperties().getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show-sql", getProperties().getProperty("spring.jpa.show-sql"));
		properties.put("hibernate.ddl-auto", getProperties().getProperty("spring.jpa.hibernate.ddl-auto"));

		em.setJpaPropertyMap(properties);
		em.afterPropertiesSet();
		return em;
	}

	@Bean(name = "transactionManagerPASCOR")
	public PlatformTransactionManager corTransactionManager( @Qualifier("entityManagerFactoryPASCOR" ) EntityManagerFactory emf ) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

}
