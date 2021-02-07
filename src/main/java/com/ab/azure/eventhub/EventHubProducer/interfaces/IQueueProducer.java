package com.ab.azure.eventhub.EventHubProducer.interfaces;

import java.util.concurrent.CompletableFuture;

public interface IQueueProducer {
	  /**
	   * Send message the given queue Name, with paritition key and the JSON message
	   *
	   * @param queueName the queue name to be used
	   * @param partitionKey the partition key for the message, this is for queues like kinesis or
	   *     kafka, if the queue is like activemq then this might not be required.
	   * @param message
	   * @return
	   */
	  CompletableFuture<String> send(String partitionKey, String message);

	  /**
	   * Method added to resolve AZ-5457
	   *
	   * @param queueName
	   * @param message
	   * @return
	   */
	  CompletableFuture<Void> send(String message);

}
