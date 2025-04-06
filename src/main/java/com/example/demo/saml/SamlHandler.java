package com.example.demo.saml;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SamlHandler {
	public static final String LOGIN = "localhost:8080/SP/SAMPLE/TestLogin";
	public static final String LOGOUT = "localhost:8080/SP/SAMPLE/TestLogout";

	public static String generateRequest(SamlRequestGenerator generator) {
		XmlMapper mapper = new XmlMapper();
		try {
			String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generator);
			Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
			deflater.setInput(result.getBytes("UTF-8"));
			deflater.finish();

			byte[] compressData = new byte[1024];
			int compressDataLength = deflater.deflate(compressData);

			byte[] resultData = Arrays.copyOf(compressData, compressDataLength);
			byte[] basedResult = Base64.getEncoder().encode(resultData);
			return Base64.getUrlEncoder().encodeToString(basedResult);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
