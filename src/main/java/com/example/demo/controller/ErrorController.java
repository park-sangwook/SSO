package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
public class ErrorController {
	@GetMapping("/error")
	public String handlerError(HttpServletRequest request, HttpServletResponse response, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

		int statusCode = Integer.parseInt(status.toString());
		response.setStatus(statusCode);
		System.out.println("error_code : "+status.toString());
		model.addAttribute("code", status.toString());
		model.addAttribute("msg", errorMessage.toString()+ "잘못된 접근 입니다.");
		return "error";
	}
}
