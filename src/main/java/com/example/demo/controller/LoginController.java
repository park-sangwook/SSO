package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.saml.SamlHandler;
import com.example.demo.saml.SamlRequestParam;
import com.example.demo.sp.WebAgent;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	
	@GetMapping(value = "/SAMPLE/TestLogin")
	public String testLogin(SamlRequestParam param) {
		System.out.println("text : "+param.getSamlRequest());
		System.out.println("test : "+SamlHandler.parswWebRequest(param.getSamlRequest()));
		return "TestLogin";
	}
	
	@GetMapping(value = "/SAMPLE/TestSso")
	public String testSso(HttpServletRequest request, HttpServletResponse response) {
		WebAgent agent = new WebAgent();
		agent.requestAuthenticate(request,response);
		return "TestSso";
	}
}
