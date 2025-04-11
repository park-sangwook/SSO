package com.example.demo.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class IDPSessionParam {
	private String userId;
	private Instant expriationDate;
}
