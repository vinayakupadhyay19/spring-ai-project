package com.vinayak.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
	
	private final ChatClient chatClient;
	
	public HelloController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	@GetMapping("/prompt")
	public String prompt(@RequestParam String message) {
		return chatClient
				.prompt(message)
				.call()
				.content();
	}
}
