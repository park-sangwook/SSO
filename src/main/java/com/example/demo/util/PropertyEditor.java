package com.example.demo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PropertyEditor {
	public static String getProperty(String name) {
		ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();
		if(applicationContext.getEnvironment().getProperty(name) == null) {
			log.info(name+" properties was not loaded");
			return null;
		}
		return applicationContext.getEnvironment().getProperty(name).toString();
	}
}
