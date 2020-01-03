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
		basePackages="br.ufpi.dadosabertosapi.database.database2.dao",
		entityManagerFactoryRef="database2EntityManager",
		transactionManagerRef="database2TransactionManager"
		)
public class Database2DBConfig {

	@Autowired
	private Environment env;

	@Bean(name="database2Context")
	public LocalContainerEntityManagerFactoryBean database2EntityManager() {
		LocalContainerEntityManagerFactoryBean em
		= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(database2DataSource());
		em.setPackagesToScan(
				new String[] { "br.ufpi.dadosabertosapi.database.database2.model" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("hibernate.hbm2ddl.auto",
//				env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect",
				env.getProperty("spring.database2-datasource.hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public PlatformTransactionManager database2TransactionManager() {

		JpaTransactionManager transactionManager
		= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				database2EntityManager().getObject());
		return transactionManager;
	}	

	@Bean
	@ConfigurationProperties(prefix="spring.database2-datasource")
	public DataSource database2DataSource() {

		return DataSourceBuilder.create().build();

	}

}
