package br.ufpi.dadosabertosapi.database.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(
		basePackages="br.ufpi.dadosabertosapi.database.database1.dao",
		entityManagerFactoryRef="database1EntityManager",
		transactionManagerRef="database1TransactionManager"
		)
public class Database1DBConfig {

	@Autowired
	private Environment env;

	@Bean(name="database1Context")
	public LocalContainerEntityManagerFactoryBean database1EntityManager() {
		LocalContainerEntityManagerFactoryBean em
		= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(database1DataSource());
		em.setPackagesToScan(
				new String[] { "br.ufpi.dadosabertosapi.database.database3.model" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("hibernate.hbm2ddl.auto",
//				env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect",
				env.getProperty("spring.database1-datasource.hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public PlatformTransactionManager database1TransactionManager() {

		JpaTransactionManager transactionManager
		= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				database1EntityManager().getObject());
		return transactionManager;
	}

	@Bean
	@ConfigurationProperties(prefix="spring.database1-datasource")
	public DataSource database1DataSource() {

		return DataSourceBuilder.create().build();

	}

}
