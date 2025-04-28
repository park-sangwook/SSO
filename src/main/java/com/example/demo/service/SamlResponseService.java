package com.example.demo.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SamlResponseParam;
import com.example.demo.util.SignatureDecryptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SamlResponseService {
	private final SignatureDecryptor decryptor;
	// 인가 작업
	public void authenticated(SamlResponseParam samlResponseParam, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(Objects.isNull(samlResponseParam.getRelayState()) || 
				Objects.isNull(samlResponseParam.getSamlResponse()) ||
				Objects.isNull(samlResponseParam.getSignature())) {
			return;
		}
			
		//String testSignature = HmacSHA512Encyptor.generateToString(samlResponseParam.getRelayState(),samlResponseParam.getSamlResponse());
		String param = String.join("",samlResponseParam.getSamlResponse(), samlResponseParam.getRelayState());
		try {
			if(!decryptor.verify(param,samlResponseParam.getSignature())) {
				log.info("Violation of SamlResponse Integrity");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.info("SSO Assertion Check Clear");
		String jSessionId = session.getId();
		session.setAttribute("SP", jSessionId);
	}

}
