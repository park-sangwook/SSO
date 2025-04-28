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

@XmlRootElement(name = "LogoutRequest", namespace = SamlSloRequestGenerator.SAML2_PROTOCOL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"issuer", "nameID"}) // 요소 순서만 지정, 속성은 제외 가능
@Data
public class SamlSloRequestGenerator {

    public static final String SAML2_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";
    public static final String SAML2_ASSERTION = "urn:oasis:names:tc:SAML:2.0:assertion";

    // 속성들 (propOrder에 포함할 필요 없음)
    @XmlAttribute(name = "ID")
    private String id = UUID.randomUUID().toString();

    @XmlAttribute(name = "Version")
    private String version = "2.0";

    @XmlAttribute(name = "IssueInstant")
    private String issueInstant;

    // 요소들 (propOrder에 포함)
    @XmlElement(name = "Issuer", namespace = SAML2_ASSERTION)
    private Issuer issuer = new Issuer();

    @XmlElement(name = "NameID", namespace = SAML2_PROTOCOL)
    private NameID nameID = new NameID();

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Issuer {
        @XmlValue
        private String isserValue;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class NameID {
        @XmlAttribute(name = "Format")
        private String format = "urn:oasis:names:tc:SAML:1.1:nameid-format:id";

        @XmlValue
        private String formatValue;

        public String getFormatValue() {
            return formatValue;
        }

        public void setFormatValue(String formatValue) {
            this.formatValue = formatValue;
        }
    }
}
