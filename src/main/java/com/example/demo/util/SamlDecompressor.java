package com.example.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.stereotype.Component;

@Component
public class SamlDecompressor {
	public String decryptSamlRequest(String samlRequest) {
		Inflater inflater = new Inflater();
		try {
			String urldecodedResult = URLDecoder.decode(samlRequest,StandardCharsets.UTF_8);
			byte[] decodedSamlRequest = Base64.getUrlDecoder().decode(urldecodedResult);
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
	
	public String getBase64ToString(String data) {
		byte[] bytes = Base64.getDecoder().decode(data.getBytes());
		return new String(bytes);
	}
}
