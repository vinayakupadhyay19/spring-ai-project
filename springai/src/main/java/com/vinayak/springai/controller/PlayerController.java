package com.vinayak.springai.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {
	
	private final ChatClient chatClient;
	
	public PlayerController(ChatClient.Builder _chatClient) {
		this.chatClient = _chatClient.build();
	}
	
	
	@GetMapping("/playername")
	public String getPlayerAchievement(@RequestParam String name) {
		
		String message = "Generate a list of Career achievement for the sportsperson {sports}.\s"
				+ "Include the Player as key and achievements as the value for it";
		
		PromptTemplate template = new PromptTemplate(message);
		
		Prompt prompt = template.create(Map.of("sports" , name));
		
		ChatResponse chatResponse = chatClient
				.prompt(prompt)
				.call()
				.chatResponse();
		return chatResponse
				.getResult()
				.getOutput()
				.getText();
	}
	
}
