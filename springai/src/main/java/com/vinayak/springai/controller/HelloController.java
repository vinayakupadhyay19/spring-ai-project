package com.vinayak.springai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
	
	private final ChatClient chatClient;
	
	@Value("classpath:/prompts/celeb-details.st")
	private Resource celebPrompt;
	
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
		//String message = "List the details of Famous personality {name} along with "
		//		+ "their Carrier achievements. Show the details in readable format.";
		
		//PromptTemplate template = new PromptTemplate(message);
		PromptTemplate template = new PromptTemplate(celebPrompt);
		
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
	
	@GetMapping("/sports")
	public String getSportsDetails(@RequestParam String name) {
		String message = "List the details of the Sports %s "
				+ "along with their Rules and Regulations."
				+ "Show the details in readable format";
		String systemMessages = "You are a smart Virtual Assistance"
				+ "Your taks is to give the details about the Sports."
				+ "If someone ask about something else and you don't know the answer ,"
				+ "Just say that you do not know the answer.";
		
		UserMessage userMesages = new UserMessage(String.format(message,name));
		
		SystemMessage systemMessage = new SystemMessage(systemMessages);
		
		Prompt propmt = new Prompt(List.of(userMesages,systemMessage)); 
		
		return chatClient
					.prompt(propmt)
					.call()
					.chatResponse()
					.getResult()
					.getOutput()
					.getText();
		
	}
	
	
}
