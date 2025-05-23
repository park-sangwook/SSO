package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA512Encyptor {
	public static String generate(String message) {
		try {
			message = message.replace("%3D", "=");
			String secretKey = PropertyEditor.getProperty(SSOParams.Config.SECRETKEY); 
			Mac mac = Mac.getInstance("HmacSHA512");

			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");

			mac.init(keySpec);

			byte[] hmacSha512 = mac.doFinal(message.getBytes());

			String encodedResult = Base64.getUrlEncoder().encodeToString(hmacSha512);
			return encodedResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String generateToString(String... args) {
		return generate(String.join("", args));
	}

	public static String generateSecretKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] secretKey = new byte[64]; // 512비트 크기
		secureRandom.nextBytes(secretKey);

		String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey);
		return encodedSecretKey;
	}

	public static void main(String[] args) {
		System.out.println("key : " + PropertyEditor.getProperty("secretKey"));
	}
	/*
	 * Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
	 * 
	 * deflater.setInput(inputBytes); deflater.finish();
	 * 
	 * 
	 */
}
