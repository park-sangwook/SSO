package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.LoginParam;
import com.example.demo.dto.OAuthParam;
import com.example.demo.dto.SamlResponseParam;
import com.example.demo.saml.SamlRequestParam;
import com.example.demo.service.HazelCastClientService;
import com.example.demo.service.LogoutService;
import com.example.demo.service.SamlResponseService;
import com.example.demo.sp.WebAgent;
import com.example.demo.util.HmacSHA512Encyptor;
import com.example.demo.util.PropertyEditor;
import com.example.demo.util.SSOParams;
import com.example.demo.util.SamlDecompressor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
	private final HazelCastClientService hazelCastClientService;
	private final SamlResponseService samlResponseService;
	private final SamlDecompressor samlDecompressor;
	private final LogoutService logoutService;
	private String userId;
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
	
	@GetMapping(value = "/SAMPLE/TestOAuth")
	public String testOauth() {
		return "TestOAuth";
	}
	@GetMapping(value = "/SAMPLE/TestOAuthLogin")
	public String testOAuthLogin(OAuthParam param,Model model) {
		WebAgent agent = new WebAgent();
		if(!agent.parseOAuthCheck(param)) {
			log.info("reqired OAuth param request is absent");
			return null; // TODO : 여기에 원래 error.html이 들어가야 한다.
		}
		model.addAttribute("data",param);
		model.addAttribute("target","https://localhost:8443/IDP/oauth/authorization");
		return "TestOAuthLogin";
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

	@PostMapping(value = "/logout")
	@ResponseBody
	public ResponseEntity<?> logout(HttpSession session, HttpServletResponse response, Model model){
		try {
			Map<String, String> map = logoutService.logout(userId);
			return ResponseEntity.ok(map);			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR");
		}
	}
	
	@PostMapping(value = "/logoutOk")
	public String logoutOk(@RequestParam String response, @RequestParam String signature, Model model, HttpSession session) {
		String testSignature = HmacSHA512Encyptor.generateToString(response);
		if(!testSignature.equals(signature)) {
			log.info("Violation of SloResponse Integrity");
		}
		session.invalidate();
		model.addAttribute("target",PropertyEditor.getProperty(SSOParams.Config.SPADDR)+"/SAMPLE/TestSso");
		model.addAttribute("method","get");
		return "response";
	}
	
	@PostMapping(value = "/authorization/sso")
	public String authorizationSSO(LoginParam loginParam,SamlResponseParam samlResponseParam, HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		session.setAttribute("IDP_ID", hazelCastClientService.getUserIdpMapping(loginParam.getId()));
		samlResponseService.authenticated(samlResponseParam, request);
		Map<String, Object> map = new HashMap<>();
		model.addAttribute("target", samlDecompressor.getBase64ToString(samlResponseParam.getRelayState()));
		model.addAttribute("method", "post");
		userId = loginParam.getId();
		Map<String, Object> mapData = new HashMap<>();
		mapData.put("id", loginParam.getId());
		map.put("data", mapData);
		model.addAttribute("data",map);
		return "response";
	}
	
	@GetMapping(value = "/signup")
	public String signup(Model model) {
		model.addAttribute("target",PropertyEditor.getProperty(SSOParams.Config.SERVERADDR)+"/signupOk");
		return "signup";
	}
	
	
	// TODO : 현재 request는 unmashalling이 되는데 response가 안되는 상태
	@PostMapping(value = "/trans")
	public ResponseEntity<?> transData(@RequestBody String data){
		return ResponseEntity.ok(samlDecompressor.decryptSamlRequest(data));
	}	
}
