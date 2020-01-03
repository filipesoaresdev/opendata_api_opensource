package br.ufpi.dadosabertosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import br.ufpi.dadosabertosapi.security.vo.CKANProperties;
import br.ufpi.dadosabertosapi.security.vo.SecurityProperties;

@SpringBootApplication
@EnableResourceServer
@EnableConfigurationProperties({SecurityProperties.class,CKANProperties.class})
public class OpenDataApiApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OpenDataApiApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OpenDataApiApplication.class, args);
	}
	
	

}
