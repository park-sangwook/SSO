package com.example.demo.sp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.Deflater;

import com.example.demo.saml.SamlHandler;
import com.example.demo.saml.SamlRequestGenerator;
import com.example.demo.util.HmacSHA512Encyptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WebAgent {
	
	public void requestAuthenticate(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(Objects.isNull(session.getAttribute("IDP_ID")) || Objects.isNull(session.getAttribute("SP"))) {
			try {
				response.sendRedirect(generatorUrl(request));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String generatorUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		String loginurl = Base64.getEncoder().encodeToString(SamlHandler.LOGIN.getBytes());
		String logouturl = Base64.getEncoder().encodeToString(SamlHandler.LOGOUT.getBytes());
		SamlRequestGenerator generator = new SamlRequestGenerator();
		SamlRequestGenerator.Issuer issuer = new SamlRequestGenerator.Issuer();
		issuer.setAssertionAddress(request.getRemoteAddr());
		generator.setIssueInstant(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toString());
		
		String samlRequest = SamlHandler.generateRequest(generator);
		
		
		
		builder.append("http://localhost:8080/SP/SAMPLE/TestLogin");
		builder.append("?loginurl="+loginurl);
		builder.append("&logouturl="+logouturl);
		builder.append("&samlRequest="+samlRequest);
		builder.append("&signature="+HmacSHA512Encyptor.generateToString(loginurl,logouturl,samlRequest));
		return builder.toString();
		
	}
}
