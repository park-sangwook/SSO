package com.example.demo.dto;

import lombok.Data;

@Data
public class SamlResponseParam {
	private String samlResponse;
	private String relayState;
	private String signature;
}
