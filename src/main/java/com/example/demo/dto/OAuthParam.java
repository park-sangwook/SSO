package com.example.demo.dto;

import lombok.Data;

@Data
public class OAuthParam {
	private String response_type;
	private String client_id;
	private String redirect_uri;
	private String scope;
	private String state;
	private String nonce; // OIDC에서 사용
}
