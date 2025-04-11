package com.example.demo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;


@Service
public class HazelCastClientService {
	private HazelcastInstance hazelcastClientInstance;
	private MultiMap<String, String> userIdpMapping;
	public HazelCastClientService(HazelcastInstance hazelcastClientInstance) {
		this.hazelcastClientInstance = hazelcastClientInstance;
		userIdpMapping = hazelcastClientInstance.getMultiMap("userIdpMapping");
	}
	
	public String getUserIdpMapping(String userId){
		Collection<String> user =  userIdpMapping.get(userId);
		return user.stream().findAny().get();
	}
	public Collection<String> getUserIdpMapping(){
		System.out.println("keySet : "+userIdpMapping.keySet());
		System.out.println("entrySet : "+userIdpMapping.entrySet());
		System.out.println("userIdpMapping : "+userIdpMapping);
		return userIdpMapping.keySet();
	}
	@PostConstruct
	public void init() {
		// 초기화 시점에 실행될 로직
		if (hazelcastClientInstance != null) {
			System.out.println("Hazelcast Client Initialized");
		} else {
			System.out.println("Hazelcast Client Failed to Initialize");
		}
	}

	@PreDestroy
	public void cleanup() {
		// 종료 시점에 실행될 로직
		if (hazelcastClientInstance != null) {
			System.out.println("Shutting down Hazelcast Client...");
			hazelcastClientInstance.shutdown(); // 클라이언트 종료
		}
	}
}
