package com.example.demo.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.variable.impl.TypedValueField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EXTaskHandler implements ExternalTaskHandler{
	
	@Autowired
	private EXWorker exWorker; 

	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
		try {
			exWorker.checkChunksCompleteTopic(externalTask.getId());
			
			Random random = new Random();
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("areChunksComplete", random.nextBoolean() ?"YES":"NO");
			
			externalTaskService.complete(externalTask,vars);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
