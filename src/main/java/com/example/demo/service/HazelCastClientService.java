package com.example.demo.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		return userIdpMapping.keySet();
	}
	
	public void removeUserIdpMapping(String userId) {
		Collection<String> user = userIdpMapping.get(userId);
		userIdpMapping.remove(user);
	}
	@PostConstruct
	public void init() {
		// 초기화 시점에 실행될 로직
		if (hazelcastClientInstance != null) {
			log.info("Hazelcast Client Initialized");
		} else {
			log.info("Hazelcast Client Failed to Initialize");
		}
	}

	@PreDestroy
	public void cleanup() {
		// 종료 시점에 실행될 로직
		if (hazelcastClientInstance != null) {
			log.info("Shutting down Hazelcast Client...");
			hazelcastClientInstance.shutdown(); // 클라이언트 종료
		}
	}
}
