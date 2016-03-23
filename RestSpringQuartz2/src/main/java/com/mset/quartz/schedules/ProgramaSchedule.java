package com.mset.quartz.schedules;
 
import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mset.quartz.jobs.QuartzJob;
import com.mset.quartz.jobs.StartJob;
import com.mset.quartz.jobs.StopJob;

import javax.servlet.ServletContext;

import java.text.SimpleDateFormat;
import java.util.Date;
 
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ProgramaSchedule{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramaSchedule.class);
    //private ServletConfig config;
    private Scheduler scheduler;
 
    public void init(ServletContext context){
        try {
        	if(context==null){
        		scheduler = StdSchedulerFactory.getDefaultScheduler();
        		scheduler.start();
        	}	
        	else
        	{
	        	StdSchedulerFactory factory = (StdSchedulerFactory) context
	                    .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);        	
	            scheduler = factory.getScheduler();
        	}
        } catch (SchedulerException e) {
        	System.out.println("Init Error: "+e);
        }
        System.out.println("Job was scheduled se inicializo ");
    }

    public void programarMills(String groupName, String jobName, String mills){
        try {
            Date startTimeForSimpleTrigger = null;
            if (!"".equals(mills) && mills.matches("-?\\d+(\\.\\d+)?")) { // isNumeric copied from commons-lang StringUtils
                startTimeForSimpleTrigger = new Date(Long.parseLong(mills));
            }
 
            JobKey jobKey = JobKey.jobKey(jobName, groupName);
            JobDetail job = newJob(QuartzJob.class).withIdentity(jobKey).requestRecovery().build();
 
            String triggerName = String.format("simple-%s", jobKey);
            Trigger simpleTrigger = newTrigger()
                    .withIdentity(triggerName, groupName)
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(0)
                            .withIntervalInSeconds(0))
                    .startAt(startTimeForSimpleTrigger)
                    .forJob(job)
                    .build();
 
            boolean jobExists = scheduler.checkExists(job.getKey());
            if (!jobExists) {
                scheduler.scheduleJob(job, simpleTrigger);
            } else {
                scheduler.rescheduleJob(simpleTrigger.getKey(), simpleTrigger);
            }
        } catch (Exception e) {
        	System.out.println("Job was not scheduled: "+e);
            LOGGER.error("Job was not scheduled", e);
        }
        System.out.println("Job was scheduled: ");
    }
    
    public void programar(String evento, String groupName, String jobName, String date){
        try {
            Date startTimeForSimpleTrigger = null;
            startTimeForSimpleTrigger =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date); 
 
            JobKey jobKey = JobKey.jobKey(jobName, groupName);
            JobDetail job=null;
            if("Start".equals(evento))
            		job=newJob(StartJob.class).withIdentity(jobKey).requestRecovery().build();
            else if("Stop".equals(evento))
            		job=newJob(StopJob.class).withIdentity(jobKey).requestRecovery().build();
 
            String triggerName = String.format("trigger-%s", jobKey);
            Trigger simpleTrigger = newTrigger()
                    .withIdentity(triggerName, groupName)
                    .withSchedule(simpleSchedule()
                            .withRepeatCount(0)
                            .withIntervalInSeconds(0))
                    .startAt(startTimeForSimpleTrigger)
                    .forJob(job)
                    .build();
 
            boolean jobExists = scheduler.checkExists(job.getKey());
            if (!jobExists) {
                scheduler.scheduleJob(job, simpleTrigger);
            } else {
                scheduler.rescheduleJob(simpleTrigger.getKey(), simpleTrigger);
            }
        } catch (Exception e) {
        	System.out.println("Job was not scheduled: "+e);
            LOGGER.error("Job was not scheduled", e);
        }
        System.out.println("Job was scheduled: ");
    }    
}