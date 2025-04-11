package com.example.demo.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.LoginParam;
import com.example.demo.dto.SamlResponseParam;
import com.example.demo.saml.SamlRequestParam;
import com.example.demo.service.HazelCastClientService;
import com.example.demo.service.SamlResponseService;
import com.example.demo.sp.WebAgent;
import com.example.demo.util.PropertyEditor;
import com.example.demo.util.SSOParams;
import com.example.demo.util.SamlDecompressor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	private final HazelCastClientService hazelCastClientService;
	private final SamlResponseService samlResponseService;
	private final SamlDecompressor samlDecompressor;
	@GetMapping(value = "/SAMPLE/TestLogin")
	public String testLogin(SamlRequestParam param,Model model,HttpServletResponse response) {
		model.addAttribute("data",param);
		model.addAttribute("addr",PropertyEditor.getProperty(SSOParams.Config.SERVERADDR));
		WebAgent agent = new WebAgent();
			try {
				if(!agent.parseWebRequest(param)) response.sendRedirect("https://localhost:8444/SP/error");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "TestLogin";
	}
	
	@GetMapping(value = "/SAMPLE/TestSso")
	public String testSso(HttpServletRequest request, HttpServletResponse response) {
		WebAgent agent = new WebAgent();
		agent.requestAuthenticate(request,response);
		return "TestSso";
	}
	
	@PostMapping(value = "/SAMPLE/TestSso")
	public String postTestSso() {
		
		return "TestSso";
	}
	
	@PostMapping(value = "/authorization/sso")
	public String authorizationSSO(LoginParam loginParam,SamlResponseParam samlResponseParam, HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		session.setAttribute("IDP_ID", hazelCastClientService.getUserIdpMapping("asdf"));
		samlResponseService.authenticated(samlResponseParam, request);
		model.addAttribute("target",samlDecompressor.getBase64ToString(samlResponseParam.getRelayState()));
		return "response";
	}
	@PostMapping(value = "/trans")
	public ResponseEntity<?> transData(@RequestBody String data){
		return ResponseEntity.ok(samlDecompressor.decryptSamlRequest(data));
	}
}
