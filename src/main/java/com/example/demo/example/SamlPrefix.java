package com.example.demo.example;

import com.example.demo.saml.SamlRequestGenerator;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
public class SamlPrefix extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (SamlRequestGenerator.SAML2_PROTOCOL.equals(namespaceUri)) {
            return "saml2p";
        } else if (SamlRequestGenerator.SAML2_ASSERTION.equals(namespaceUri)) {
            return "saml2";
        }
        return suggestion; // 기본 prefix 반환
    }
}
