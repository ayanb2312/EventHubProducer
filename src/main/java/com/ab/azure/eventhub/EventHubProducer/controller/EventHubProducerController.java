package com.ab.azure.eventhub.EventHubProducer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.azure.eventhub.EventHubProducer.EventHubProducer;

@RestController
public class EventHubProducerController {
	private static EventHubProducer ehProducer;
	
	@GetMapping("/send")
	public String ehSend() {  
		System.out.println("About to send message to EH.");
		ehProducer.send("pk", "sample message 1");
		System.out.println("Message sent to EH.");
		
		return "Message sent to EH successfully.";
	} 

}
