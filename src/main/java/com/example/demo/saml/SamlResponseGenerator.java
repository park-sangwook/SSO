package com.example.demo.saml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import lombok.Data;

@XmlRootElement(name = "Response", namespace = SamlResponseGenerator.SAML2_PROTOCOL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"issuer", "signature", "status", "assertion"})
@Data
public class SamlResponseGenerator {

    public static final String SAML2_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";
    public static final String SAML2_ASSERTION = "urn:oasis:names:tc:SAML:2.0:assertion";
    public static final String XML_SIG_NAMESPACE = "http://www.w3.org/2000/09/xmldsig#";

    @XmlAttribute(name = "Destination")
    private String destination;

    @XmlAttribute(name = "ID")
    private String id;

    @XmlAttribute(name = "InResponseTo")
    private String inResponseTo;

    @XmlAttribute(name = "IssueInstant")
    private String issueInstant;

    @XmlAttribute(name = "Version")
    private String version = "2.0";

    @XmlElement(name = "Issuer", namespace = SAML2_ASSERTION)
    private Issuer issuer = new Issuer();

    @XmlElement(name = "Signature", namespace = XML_SIG_NAMESPACE)
    private Signature signature = new Signature();

    @XmlElement(name = "Status", namespace = SAML2_PROTOCOL)
    private Status status = new Status();

    @XmlElement(name = "Assertion", namespace = SAML2_ASSERTION)
    private Assertion assertion = new Assertion();

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Issuer {
        @XmlValue
        private String value = "{Response Issuer}";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Signature {
        @XmlValue
        private String digitalSignature = "{디지털 서명}";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Status {
        @XmlElement(name = "StatusCode", namespace = SAML2_PROTOCOL)
        private StatusCode statusCode = new StatusCode();

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        public static class StatusCode {
            @XmlAttribute(name = "Value")
            private String value = "urn:oasis:names:tc:SAML:2.0:status:Success";
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class Assertion {
        @XmlAttribute(name = "ID")
        private String id = "{NAVER WORKS에서 발행하는 ID}";

        @XmlAttribute(name = "IssueInstant")
        private String issueInstant = "{SAML Response 생성 일시}";

        @XmlAttribute(name = "Version")
        private String version = "2.0";

        @XmlElement(name = "Issuer", namespace = SAML2_ASSERTION)
        private Issuer assertionIssuer = new Issuer();

        @XmlElement(name = "Subject", namespace = SAML2_ASSERTION)
        private Subject subject = new Subject();

        @XmlElement(name = "Conditions", namespace = SAML2_ASSERTION)
        private Conditions conditions = new Conditions();

        @XmlElement(name = "AuthnStatement", namespace = SAML2_ASSERTION)
        private AuthnStatement authnStatement = new AuthnStatement();

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        public static class Subject {
            @XmlElement(name = "NameID", namespace = SAML2_ASSERTION)
            private NameID nameID = new NameID();

            @XmlElement(name = "SubjectConfirmation", namespace = SAML2_ASSERTION)
            private SubjectConfirmation subjectConfirmation = new SubjectConfirmation();

            @XmlAccessorType(XmlAccessType.FIELD)
            @Data
            public static class NameID {
                @XmlAttribute(name = "Format")
                private String format = "unspecified";

                @XmlValue
                private String value = "{로그인한 사용자 NAVER WORKS ID}";
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @Data
            public static class SubjectConfirmation {
                @XmlAttribute(name = "Method")
                private String method = "urn:oasis:names:tc:SAML:2.0:cm:bearer";

                @XmlElement(name = "SubjectConfirmationData", namespace = SAML2_ASSERTION)
                private SubjectConfirmationData subjectConfirmationData = new SubjectConfirmationData();

                @XmlAccessorType(XmlAccessType.FIELD)
                @Data
                public static class SubjectConfirmationData {
                    @XmlAttribute(name = "InResponseTo")
                    private String inResponseTo = "{SAML Request에 포함된 ID}";

                    @XmlAttribute(name = "NotOnOrAfter")
                    private String notOnOrAfter = "{SAML Response 종료 일시}";

                    @XmlAttribute(name = "Recipient")
                    private String recipient = "{ACS URL}";
                }
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        public static class Conditions {
            @XmlAttribute(name = "NotBefore")
            private String notBefore = "{SAML Response 시작 일시}";

            @XmlAttribute(name = "NotOnOrAfter")
            private String notOnOrAfter = "{SAML Response 종료 일시}";

            @XmlElement(name = "AudienceRestriction", namespace = SAML2_ASSERTION)
            private AudienceRestriction audienceRestriction = new AudienceRestriction();

            @XmlAccessorType(XmlAccessType.FIELD)
            @Data
            public static class AudienceRestriction {
                @XmlElement(name = "Audience", namespace = SAML2_ASSERTION)
                private String audience = "{NAVER WORKS에 등록한 SP Issuer}";
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        public static class AuthnStatement {
            @XmlAttribute(name = "AuthnInstant")
            private String authnInstant = "{SAML Response 생성 일시}";

            @XmlAttribute(name = "SessionIndex")
            private String sessionIndex = "{NAVER WORKS에서 발행하는 ID}";

            @XmlElement(name = "AuthnContext", namespace = SAML2_ASSERTION)
            private AuthnContext authnContext = new AuthnContext();

            @XmlAccessorType(XmlAccessType.FIELD)
            @Data
            public static class AuthnContext {
                @XmlElement(name = "AuthnContextClassRef", namespace = SAML2_ASSERTION)
                private String authnContextClassRef = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
            }
        }
    }
}
