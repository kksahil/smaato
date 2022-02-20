package com.smaato.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.smaato.Scheduler.LogJob;

//import java.util.UUID;

@Configuration
public class QuartxConfig {
	@Bean
	public JobDetail jobLogDetails() {
		return JobBuilder.newJob(LogJob.class).withIdentity("LogJob").storeDurably().build();
	}
@Bean
	public Trigger jobLogTrigger(JobDetail jobDetails) {
		return TriggerBuilder.newTrigger().forJob(jobDetails).withIdentity("TriggerLog")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * ? * * *")).build();
	}
	
	
}