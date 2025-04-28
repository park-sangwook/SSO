package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.util.ErrorException;

@ControllerAdvice
public class ErrorController {
	
	@ExceptionHandler(ErrorException.class)
	public String exception(ErrorException e, Model model) {
		model.addAttribute("errorMsg",e.getMsg());
		return "error";
	}
}
