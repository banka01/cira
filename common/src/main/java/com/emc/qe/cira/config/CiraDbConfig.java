package com.emc.qe.cira.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.emc.qe.cira.repository")
@PropertySource("classpath:/cira-db.properties")

public class CiraDbConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource ciraDataSource() {
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("cira.db.driver"));
		dataSource.setUrl(env.getProperty("cira.db.url"));
		dataSource.setUsername(env.getProperty("cira.db.username"));
		dataSource.setPassword(env.getProperty("cira.db.passwd"));

		dataSource.setInitialSize(1);
		dataSource.setMaxTotal(5);
		dataSource.setMaxIdle(2);

		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(ciraDataSource());
		entityManagerFactoryBean.setPersistenceUnitName("cira_system_repository");
		entityManagerFactoryBean.setPackagesToScan("com.emc.qe.cira.model");
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());

		final Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("cira.db.hibernate.hbm2ddl_auto"));
		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();

	}

	@Bean
	HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabasePlatform(env.getProperty("cira.db.hibernate.dialect"));
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setGenerateDdl(false);
		return jpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;

	}

}