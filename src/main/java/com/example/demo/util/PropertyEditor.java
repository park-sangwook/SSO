package com.example.demo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PropertyEditor {
	public static String getProperty(String name) {
		ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();
		if(applicationContext.getEnvironment().getProperty(name) == null) {
			System.out.println(name+" properties was not loaded");
			return null;
		}
		return applicationContext.getEnvironment().getProperty(name).toString();
	}
}
