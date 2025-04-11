package com.example.demo.saml;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.example.demo.example.SamlPrefix;
import com.example.demo.util.PropertyEditor;
import com.example.demo.util.SSOParams;

public class SamlHandler {
	public static final String LOGIN = PropertyEditor.getProperty(SSOParams.Config.SPADDR)+"/SAMPLE/TestLogin";
	public static final String LOGOUT = PropertyEditor.getProperty(SSOParams.Config.SPADDR)+"/SAMPLE/TestLogout";

	public static String generateRequest(Object generator) {
		try {
			JAXBContext context = JAXBContext.newInstance(generator.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new SamlPrefix());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(generator, stringWriter);
			Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
			deflater.setInput(stringWriter.toString().getBytes("UTF-8"));
			deflater.finish();

			byte[] compressData = new byte[1024];
			int compressDataLength = deflater.deflate(compressData);

			byte[] resultData = Arrays.copyOf(compressData, compressDataLength);
			String encodedResult = Base64.getUrlEncoder().encodeToString(resultData);
			return URLEncoder.encode(encodedResult,StandardCharsets.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String parswWebRequest(String samlRequest) {
		Inflater inflater = new Inflater();
		try {
			byte[] urldecodedResult = Base64.getUrlDecoder().decode(samlRequest);
			byte[] decodedSamlRequest = Base64.getDecoder().decode(urldecodedResult);
			inflater.setInput(decodedSamlRequest);
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(decodedSamlRequest.length);
			
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int byteCount;
				byteCount = inflater.inflate(buffer);
				outputStream.write(buffer, 0, byteCount);
			}

			inflater.end();
			return new String(outputStream.toByteArray(), "UTF-8");

		} catch (DataFormatException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
