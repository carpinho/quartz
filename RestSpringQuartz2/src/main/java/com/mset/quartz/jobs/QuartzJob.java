package com.mset.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzJob implements Job {
    private static final Logger LOGGER =  LoggerFactory.getLogger(QuartzJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        try {
        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = new Date();
            LOGGER.debug("Scheduled Job ("+jobDetail.getKey()+") started - "+ dateFormat.format(date).toString());
            System.out.println("Scheduled Job ("+jobDetail.getKey()+") started - "+ dateFormat.format(date).toString());
            for(int i=1;i<5;i++){
	            try {
	            	System.out.println("Iteracion "+jobDetail.getKey()+":"+i+" Objeto: "+this);
	                Thread.sleep(2000);                 //1000 milliseconds is one second.
	            } catch(InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }
        	}
            date = new Date();
            System.out.println("Scheduled Job ("+jobDetail.getKey()+") terminate - "+ dateFormat.format(date).toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Could not execute job: {}, Exception details: {}", jobDetail.getKey(), e);
            System.out.println("Could not execute job: "+jobDetail.getKey()+" Error:"+e);
            throw new JobExecutionException(e);
        }
    }
}