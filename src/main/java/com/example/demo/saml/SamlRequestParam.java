package com.example.demo.saml;

import lombok.Data;

@Data
public class SamlRequestParam {
	private String loginurl;
	private String logouturl;
	private String samlRequest;
	private String signature;
}
