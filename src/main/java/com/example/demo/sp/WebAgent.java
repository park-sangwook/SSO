package com.example.demo.sp;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Objects;

import com.example.demo.saml.SamlHandler;
import com.example.demo.saml.SamlRequestGenerator;
import com.example.demo.saml.SamlRequestParam;
import com.example.demo.util.HmacSHA512Encyptor;
import com.example.demo.util.PropertyEditor;
import com.example.demo.util.SSOParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WebAgent {
	
	public void requestAuthenticate(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		System.out.println("SP : "+session.getAttribute("SP"));
		System.out.println("IDP_ID : "+session.getAttribute("IDP_ID"));
		if(Objects.isNull(session.getAttribute("IDP_ID")) && Objects.isNull(session.getAttribute("SP"))) {
			try {
				response.sendRedirect(generatorUrl(request));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String generatorUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		String loginurl = Base64.getEncoder().encodeToString(SamlHandler.LOGIN.getBytes());
		String logouturl = Base64.getEncoder().encodeToString(SamlHandler.LOGOUT.getBytes());
		String relayState = Base64.getEncoder().encodeToString(request.getRequestURL().toString().getBytes());
		SamlRequestGenerator generator = new SamlRequestGenerator();
		SamlRequestGenerator.Issuer issuer = new SamlRequestGenerator.Issuer();
		generator.setAssertionConsumerServiceURL(request.getRemoteAddr());
		generator.setIssueInstant(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toString());
		issuer.setSpIssuer(PropertyEditor.getProperty(SSOParams.Config.SPADDR));
		generator.setIssuer(issuer);
		String samlRequest = SamlHandler.generateRequest(generator);
		builder.append(SamlHandler.LOGIN);
		builder.append("?loginurl="+loginurl);
		builder.append("&logouturl="+logouturl);
		builder.append("&samlRequest="+samlRequest);
		builder.append("&relayState="+relayState);
		builder.append("&signature="+HmacSHA512Encyptor.generateToString(loginurl,logouturl,samlRequest,relayState));
		return builder.toString();
		
	}
	
	public boolean parseWebRequest(SamlRequestParam samlRequestParam) {
		if(Objects.isNull(samlRequestParam.getSamlRequest()) || Objects.isNull(samlRequestParam.getSignature())
				|| Objects.isNull(samlRequestParam.getLoginurl()) || Objects.isNull(samlRequestParam.getLogouturl()))return false;
		String testSignature = HmacSHA512Encyptor.generateToString(samlRequestParam.getLoginurl(),samlRequestParam.getLogouturl(),
				samlRequestParam.getSamlRequest(),samlRequestParam.getRelayState());
		if(!testSignature.equals(samlRequestParam.getSignature())) {
			System.out.println("signature : "+samlRequestParam.getSignature());
			System.out.println("test : "+testSignature);
			return false;
		}
		return true;
	}
}
