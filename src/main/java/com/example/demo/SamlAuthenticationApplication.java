package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class SamlAuthenticationApplication{

	public static void main(String[] args) {
		System.setProperty("javax.xml.bind.context.factory", "com.sun.xml.bind.v2.ContextFactory");
		SpringApplication.run(SamlAuthenticationApplication.class, args);
	}

}
