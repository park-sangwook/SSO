package com.example.demo.util;

import lombok.Getter;

@Getter
public class ErrorException extends Exception{
	private static final long serialVersionUID = 1L;
	private String msg;
	public ErrorException(String error) {
		super(error);
		this.msg = error;
	}
}
