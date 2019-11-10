package com.example.demo.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EXWorker {
	
	@Async("checkChunksCompleteExecutor")
	public void checkChunksCompleteTopic(String id) throws InterruptedException {
	    System.out.println("Started  " + id + " " + Thread.currentThread().getName());
	    Thread.sleep(1000);
	    System.out.println("Finished " + Thread.currentThread().getName());
	}

}
