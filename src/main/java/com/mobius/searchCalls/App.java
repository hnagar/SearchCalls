package com.mobius.searchCalls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

	   @SuppressWarnings("deprecation")
		public static void main(String[] args) {
	  	ExecutorService executor = Executors.newFixedThreadPool(1);
	    for (int i = 0; i < 1; i++) {
	        Runnable worker = new SearchCallsScala(" " + i);
	        executor.execute(worker);
	        
	      }
	    executor.shutdown();
	    while (!executor.isTerminated()) {
	    }
//	    System.out.println("Finished all threads");
	  //  logger.info("Finished all threads");
	}
}
