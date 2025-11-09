package com.vinayak.springai.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
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
				.chatResponse()
				.getResult()
				.getOutput()
				.getText();
	}
	
	@GetMapping("/celeb")
	public String getCelebDetails(@RequestParam String name) {
		String message = "List the details of Famous personality {name} along with "
				+ "their Carrier achievments. Show the details in readable format.";
		
		PromptTemplate template = new PromptTemplate(message);
		
		Prompt prompt = template.create(
				Map.of("name" , name)
		);
		
		return chatClient
				.prompt(prompt)
				.call()
				.chatResponse()
				.getResult()
				.getOutput()
				.getText();
		
	}
	
	
}
