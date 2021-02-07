package com.ab.azure.eventhub.EventHubProducer.config;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.ab.azure.eventhub.EventHubProducer.EventHubProducer;
import com.ab.azure.eventhub.EventHubProducer.interfaces.IQueueProducer;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;

@Configuration
public class EventHubProducerConfig {
    
	@Value("Endpoint=sb://eventhubnamespace-1.servicebus.windows.net/;SharedAccessKeyName=PreviewDataPolicy;SharedAccessKey=O0JaxWSnBh0PoxfmxqEOHnQIulITEiX7C7nCVgsVV9w=;EntityPath=eventhub1")
	private String connectionString;
	
	@Value("${azure.eventhub.producer.eh-client-no:1}")
	private int eventHubClientNo;
	
	@Bean(name = "ctsWriterClientEventHubProducerClient")
	EventHubClient eventHubClient() throws EventHubException, IOException {

	  ConnectionStringBuilder connStr = new ConnectionStringBuilder(connectionString);

	  ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
	  EventHubClient ehClient =
	      EventHubClient.createSync(
	          connStr.toString(),
	          executor); // EventHubClient.createFromConnectionStringSync(connStr.toString());

	  return ehClient;
	}
	
	@Bean("queueProducer")
	public IQueueProducer getCtsEventHubProducer(BeanFactory beanFactory) {
	  EventHubClient[] eventHubClients = new EventHubClient[eventHubClientNo];
	  for (int i = 0; i < eventHubClientNo; i++) {
	    eventHubClients[i] =
	        beanFactory.getBean("ctsWriterClientEventHubProducerClient", EventHubClient.class);
	  }
	  return new EventHubProducer(eventHubClients);
	}

}
