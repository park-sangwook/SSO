package com.example.demo.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SignatureDecryptor {// 로그인에서 서명 검증은 X.509로 이루어지며, ResponseParam의 Decryptor
	// data = responseParam, param = response.getSignature()
	public boolean verify(String data, String param) throws Exception {
		Resource resource = new ClassPathResource("static/security/public_key.pem");
		String publicKeyContent = new String(Files.readAllBytes(resource.getFile().toPath()));
		publicKeyContent = publicKeyContent.replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");

		byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        
        String sigBytes = URLDecoder.decode(param,StandardCharsets.UTF_8);
        byte[] decodedData = Base64.getUrlDecoder().decode(sigBytes);
        
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        boolean flag = signature.verify(decodedData);
        
        return flag;
	}
}
