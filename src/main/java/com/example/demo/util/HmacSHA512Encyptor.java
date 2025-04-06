package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA512Encyptor {
	public static String generate(String message) {
		try {
			String secretKey = generateSecretKey(); // 비밀 키
			Mac mac = Mac.getInstance("HmacSHA512");

			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");

			mac.init(keySpec);

			byte[] hmacSha512 = mac.doFinal(message.getBytes());

			String encodedResult = Base64.getEncoder().encodeToString(hmacSha512);

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
		System.out.println("key : " + generateSecretKey());
	}
	/*
	 * Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
	 * 
	 * deflater.setInput(inputBytes); deflater.finish();
	 * 
	 * 
	 */
}
