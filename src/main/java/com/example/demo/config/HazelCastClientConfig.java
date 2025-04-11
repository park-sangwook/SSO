package com.example.demo.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelCastClientConfig {

	@Bean
    public HazelcastInstance hazelcastClientInstance() {
        // 클라이언트 네트워크 설정 객체 생성
        ClientConfig clientConfig = new ClientConfig();
        InetAddress localHost;
		try {
			localHost = InetAddress.getLocalHost();
			String ipAddress = localHost.getHostAddress();
			clientConfig.getNetworkConfig().addAddress(ipAddress+":43693"); // 서버의 IP와 포트 설정
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}