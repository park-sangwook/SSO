package com.example.demo.saml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SamlRequestParam {
	private String loginurl;
	private String logouturl;
	private String samlRequest;
	private String signature;
	private String relayState;
}
