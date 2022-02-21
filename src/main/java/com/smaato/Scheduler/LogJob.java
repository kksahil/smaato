package com.smaato.Scheduler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.smaato.services.CounterRest;


public class LogJob  implements Job {
  @Override
  public void execute(JobExecutionContext context) {
	  
	  final Logger logger = LogManager.getLogger(this.getClass());
	  
	  System.out.println("Saving Log Of last Minute : " + CounterRest.counter + " Total Unique ID counts : " + CounterRest.counter.keySet().size());
	  logger.info(CounterRest.counter.toString() + " Unique ID counts : " + CounterRest.counter.keySet().size());
	  CounterRest.counter.clear();
  }
}