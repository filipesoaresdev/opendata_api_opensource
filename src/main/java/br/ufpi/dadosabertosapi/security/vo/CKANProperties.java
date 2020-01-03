package br.ufpi.dadosabertosapi.security.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="ckan")
public class CKANProperties {

	private String ckankey;
	private String resourceCreateUrl;
	
	public String getCkankey() {
		return ckankey;
	}
	public void setCkankey(String ckankey) {
		this.ckankey = ckankey;
	}
	public String getResourceCreateUrl() {
		return resourceCreateUrl;
	}
	public void setResourceCreateUrl(String resourceCreateUrl) {
		this.resourceCreateUrl = resourceCreateUrl;
	}
	
	
	
}
