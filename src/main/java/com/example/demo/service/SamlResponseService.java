package com.example.demo.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SamlResponseService {
	// 인가 작업
	public void authenticated(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String jSessionId = session.getId();
		session.setAttribute("SP", jSessionId);
	}

}
