package com.example.demo.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.saml.SamlHandler;
import com.example.demo.saml.SamlSloRequestGenerator;
import com.example.demo.util.HmacSHA512Encyptor;
import com.example.demo.util.PropertyEditor;
import com.example.demo.util.SSOParams;

@Service
public class LogoutService {
	public Map<String, String> logout(String userId){
		SamlSloRequestGenerator generator = new SamlSloRequestGenerator();
		generator.setIssueInstant(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toString());
		SamlSloRequestGenerator.Issuer issuer = new SamlSloRequestGenerator.Issuer();
		issuer.setIsserValue(PropertyEditor.getProperty(SSOParams.Config.SPADDR));
		
		SamlSloRequestGenerator.NameID name = new SamlSloRequestGenerator.NameID();
		name.setFormatValue(userId);
		
		generator.setIssuer(issuer);
		generator.setNameID(name);

		String slo = SamlHandler.generateRequest(generator);
		String idp = PropertyEditor.getProperty(SSOParams.Config.SERVERADDR)+"/logout";
		String signature = HmacSHA512Encyptor.generateToString(slo);
		Map<String, String> map = new HashMap<>();
		map.put("slo", slo);
		map.put("idp", idp);
		map.put("signature", signature);
		return map;
	}
}
