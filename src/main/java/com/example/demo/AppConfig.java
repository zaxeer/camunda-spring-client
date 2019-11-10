package com.example.demo;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.handler.EXTaskHandler;

@Configuration
public class AppConfig {

	@Bean
	ExternalTaskClient createExternalTaskClient() {
		System.out.println("creating camund tasks clinet");
		ExternalTaskClientBuilder externalTaskClientBuilder = ExternalTaskClient.create();
		externalTaskClientBuilder.baseUrl("http://localhost:8080/engine-rest");
		externalTaskClientBuilder.lockDuration(1000);
		ExternalTaskClient externalTaskClient = externalTaskClientBuilder.build();
		TopicSubscriptionBuilder subscriptionBuilder = externalTaskClient.subscribe("checkChunksCompleteTopic");
		subscriptionBuilder.handler(createExTaskHandler());
		subscriptionBuilder.open();
		return externalTaskClient;
	}

	@Bean(name = "checkChunksCompleteExecutor")
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.setThreadNamePrefix("checkChunksCompleteTopic");
		executor.initialize();
		return executor;
	}
	
	@Bean
	EXTaskHandler createExTaskHandler() {
		return new EXTaskHandler();
	}

}
