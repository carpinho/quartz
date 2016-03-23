package com.mset.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mset.VO.ListadoProgramas;
import com.mset.VO.PublicidadVO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StopJob implements Job {
    private static final Logger LOGGER =  LoggerFactory.getLogger(StopJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        try {
        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = new Date();
            LOGGER.debug("Scheduled Job ("+jobDetail.getKey()+") Stop CIRES - "+ dateFormat.format(date).toString());
            System.out.println("Scheduled Job ("+jobDetail.getKey()+") Stop CIRES - "+ dateFormat.format(date).toString());
            
            if(jobDetail.getKey().toString().contains("telecinco"))
            	deletePublicidad("telecinco");
            else if(jobDetail.getKey().toString().contains("cuatro"))
            	deletePublicidad("cuatro");
            else if(jobDetail.getKey().toString().contains("divinity"))
            	deletePublicidad("divinity");
            
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Could not execute job: {}, Exception details: {}", jobDetail.getKey(), e);
            System.out.println("Could not execute job: "+jobDetail.getKey()+" Error:"+e);
            throw new JobExecutionException(e);
        }
    }
    private void deletePublicidad(String canal){
    	ListadoProgramas.getPublicidades().remove(canal);
    	System.out.println("Canal: "+canal+" Publicidad Eliminada");
    }    
}