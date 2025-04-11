package com.example.demo.example;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.example.demo.saml.SamlRequestGenerator;

public class SamlRequest {
	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(SamlRequestGenerator.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new SamlPrefix());
			SamlRequestGenerator generator2 = new SamlRequestGenerator();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(generator2, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
