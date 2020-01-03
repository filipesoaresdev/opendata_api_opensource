package br.ufpi.dadosabertosapi.security.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;

import br.ufpi.dadosabertosapi.security.vo.SecurityProperties;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(securityProperties.getPrivateKey());
		converter.setVerifierKey(securityProperties.getPublicKey());
		return converter;
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		Map<String, CorsConfiguration> corsConfigMap = new HashMap<>();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    //TODO: Make configurable
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedMethods(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    corsConfigMap.put("/oauth/token", config);
	    
		endpoints.tokenStore(tokenStore())
				.authenticationManager(authenticationManager)
				.accessTokenConverter(tokenEnhancer())
				.getFrameworkEndpointHandlerMapping().setCorsConfigurations(corsConfigMap);
		
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAutenticated()");
		
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory().withClient(securityProperties.getClientId()).secret(new BCryptPasswordEncoder().encode(securityProperties.getClientSecret()))
				.scopes("read","write")
				.authorizedGrantTypes("password","refresh_token")
				.accessTokenValiditySeconds(20000)
				.refreshTokenValiditySeconds(0);
		
	}
	
}
