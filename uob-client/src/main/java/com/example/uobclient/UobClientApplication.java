package com.example.uobclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UobClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(UobClientApplication.class, args);
	}

	@PostConstruct
	public void initSsl(){
		System.setProperty("javax.net.ssl.keyStore", Thread.currentThread().getContextClassLoader().getResource("client.jks").getPath());
		System.setProperty("javax.net.ssl.keyStorePassword", "demo");
		System.setProperty("javax.net.ssl.trustStore", Thread.currentThread().getContextClassLoader().getResource("client.jks").getPath());
		System.setProperty("javax.net.ssl.trustStorePassword", "demo");
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				(hostname,sslSession) -> {
					if (hostname.equals("localhost")) {
						return true;
					}
					return false;
				});
	}

	@Bean
	public RestTemplate template() throws Exception{
		RestTemplate template = new RestTemplate();
		return template;
	}
}
