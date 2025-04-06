package com.example.demo.saml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@JacksonXmlRootElement(localName = "saml2p:AuthnRequest")
@Data
public class SamlRequestGenerator {
	@JacksonXmlProperty(localName = "xmlns:saml2p",isAttribute = true)
	private String samlVersion = "urn:oasis:names:tc:SAML:2.0:protocol";
	
	@JacksonXmlProperty(localName = "id",isAttribute = true)
	private String id = "1";
	
	@JacksonXmlProperty(localName = "IssueInstant",isAttribute = true)
	private String issueInstant = "now";
	
	@JacksonXmlProperty(localName = "ProtocolBinding",isAttribute = true)
	private String protocolBinding = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect-Deflate";
	
	@Data
	public static class Issuer{		
		@JacksonXmlProperty(localName = "xmlns:saml2",isAttribute = true)
		private String assertionAddress = "urn:oasis:names:tc:SAML:2.0:assertion";
	}
	@JacksonXmlProperty(localName = "saml2:issuer")
	private Issuer issuer = new Issuer();

	@JacksonXmlProperty(localName = "saml2p:NameIDPolicy")
	private PolicyFormat policyFormat = new PolicyFormat();
	
	@Data
	@JacksonXmlRootElement
	public static class PolicyFormat{
		@JacksonXmlProperty(localName = "Format",isAttribute = true)
		private String policyFormat="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";		
	}
	
	
}
