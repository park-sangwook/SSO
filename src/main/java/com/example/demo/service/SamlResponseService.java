package com.example.demo.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SamlResponseParam;
import com.example.demo.util.HmacSHA512Encyptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class SamlResponseService {
	// 인가 작업
	public void authenticated(SamlResponseParam samlResponseParam, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(Objects.isNull(samlResponseParam.getRelayState()) || 
				Objects.isNull(samlResponseParam.getSamlResponse()) ||
				Objects.isNull(samlResponseParam.getSignature())) {
			return;
		}
			
		String testSignature = HmacSHA512Encyptor.generateToString(samlResponseParam.getRelayState(),samlResponseParam.getSamlResponse());
		if(!testSignature.equals(samlResponseParam.getSignature())) {
			return;
		}
		String jSessionId = session.getId();
		session.setAttribute("SP", jSessionId);
	}

}
