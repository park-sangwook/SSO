package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class DBConfig {

	@Bean
	public DataSource dataSource() {
		HikariConfig dataSource = new HikariConfig();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/project");
		dataSource.setUsername("jspuser");
		dataSource.setPassword("123456");
		return new HikariDataSource(dataSource);
	}
	
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowCredentials(true);
//		configuration.addAllowedHeader("*");
//		configuration.addAllowedOrigin("http://localhost:8081");
//		configuration.addAllowedOrigin("http://localhost:8080");
//		
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return new CorsFilter(source);
//	}
}
