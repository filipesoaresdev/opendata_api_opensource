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
		basePackages="br.ufpi.dadosabertosapi.database.local.dao",
		entityManagerFactoryRef="localContext",
		transactionManagerRef="localTransactionManager"
		)
public class LocalDBConfig { 
	@Autowired
    private Environment env;
 
    @Bean(name="localContext")
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource());
        em.setPackagesToScan(
          new String[] { "br.ufpi.dadosabertosapi.database.local.model" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
          "update");
        properties.put("hibernate.dialect",
          env.getProperty("spring.local-datasource.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
 
        return em;
    }
    
    @Bean
    public PlatformTransactionManager localTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
        		localEntityManager().getObject());
        return transactionManager;
    }
		@Bean
		@ConfigurationProperties(prefix="spring.local-datasource")
		public DataSource localDataSource() {
			
			return DataSourceBuilder.create().build();
			
		}
	
}
