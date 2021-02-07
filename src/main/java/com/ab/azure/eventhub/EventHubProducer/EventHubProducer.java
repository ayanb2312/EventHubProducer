package com.ab.azure.eventhub.EventHubProducer;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ab.azure.eventhub.EventHubProducer.interfaces.IQueueProducer;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;

public class EventHubProducer implements IQueueProducer {
	private static final Logger logger = LoggerFactory.getLogger(EventHubProducer.class);
	private final EventHubClient[] eventHubClients;
	private final Random random = new Random();
	
	public EventHubProducer(EventHubClient[] eventHubClients) {
	    this.eventHubClients = eventHubClients;
	  }

	@Override
	public CompletableFuture<String> send(String partitionKey, String message) {
	    logger.debug(
	        "{} service sending message {} to event-hub with partition key {}",
	        MDC.get("DemoApp"),
	        message,
	        partitionKey);

	    EventData producedEventMessage = EventData.create(message.getBytes());
	    producedEventMessage.getProperties().putAll(MDC.getCopyOfContextMap());
	    eventHubClients[random.nextInt(eventHubClients.length)].send(
	        producedEventMessage, partitionKey);
	    return CompletableFuture.supplyAsync(() -> "Completed");
	}

	@Override
	public CompletableFuture<Void> send(String message) {
	    logger.debug(
	        "{} service sending message {} to event-hub",
	        MDC.get("DemoApp"),
	        message);

	    EventData producedEventMessage = EventData.create(message.getBytes());
	    producedEventMessage.getProperties().putAll(MDC.getCopyOfContextMap());
	    return eventHubClients[random.nextInt(eventHubClients.length)].send(producedEventMessage);
	}

}
