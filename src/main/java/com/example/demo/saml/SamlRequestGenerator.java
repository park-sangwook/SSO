package com.example.demo.saml;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import lombok.Data;

@XmlRootElement(name = "AuthnRequest", namespace = SamlRequestGenerator.SAML2_PROTOCOL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"issuer", "nameIDPolicy"}) // 순서 보장
@Data
public class SamlRequestGenerator {

    public static final String SAML2_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";
    public static final String SAML2_ASSERTION = "urn:oasis:names:tc:SAML:2.0:assertion";

    @XmlAttribute(name = "AssertionConsumerServiceURL")
    private String assertionConsumerServiceURL;

    @XmlAttribute(name = "ID")
    private String id = UUID.randomUUID().toString();

    @XmlAttribute(name = "IssueInstant")
    private String issueInstant;

    @XmlAttribute(name = "ProtocolBinding")
    private String protocolBinding = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect-Deflate";

    @XmlAttribute(name = "ProviderName")
    private String providerName = "SAMPLE_SYSTEM";

    @XmlAttribute(name = "Version")
    private String version = "2.0";

    @XmlElement(name = "Issuer", namespace = SAML2_ASSERTION)
    private Issuer issuer = new Issuer();

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Issuer {
        @XmlValue
        private String spIssuer;
    }

    @XmlElement(name = "NameIDPolicy", namespace = SAML2_PROTOCOL)
    private NameIDPolicy nameIDPolicy = new NameIDPolicy();

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class NameIDPolicy {
        @XmlAttribute(name = "Format")
        private String format = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
    }
}
