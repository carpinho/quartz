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

public class MetadatosJob implements Job {
    private static final Logger LOGGER =  LoggerFactory.getLogger(MetadatosJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        try {
        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = new Date();
            LOGGER.debug("Scheduled Job ("+jobDetail.getKey()+") Metadatos - "+ dateFormat.format(date).toString());
            //System.out.println("Scheduled Job ("+jobDetail.getKey()+") Metadatos - "+ dateFormat.format(date).toString());
            
            if(jobDetail.getKey().toString().contains("telecinco"))
            	executePublicidad("telecinco");
            else if(jobDetail.getKey().toString().contains("cuatro"))
            	executePublicidad("cuatro");
            else if(jobDetail.getKey().toString().contains("divinity"))
            	executePublicidad("divinity");

            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Could not execute job: {}, Exception details: {}", jobDetail.getKey(), e);
            System.out.println("Could not execute job: "+jobDetail.getKey()+" Metadatos Error:"+e);
            throw new JobExecutionException(e);
        }
    }
    private void executePublicidad(String canal){
    	if(!ListadoProgramas.getPublicidades().containsKey(canal)){
    		PublicidadVO pu=new PublicidadVO();
    		pu.setCanal(canal);
    		ListadoProgramas.addPublicidad(pu);
    	}
    	System.out.println("Canal: "+canal+" Publicidad: "+ListadoProgramas.getPublicidades().get(canal).getDesc());
    }
}