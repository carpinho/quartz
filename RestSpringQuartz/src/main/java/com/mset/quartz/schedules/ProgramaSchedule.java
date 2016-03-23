package com.mset.quartz.schedules;
 
import com.mset.VO.ProgramaVO;
import com.mset.quartz.jobs.MetadatosJob;
import com.mset.quartz.jobs.QuartzJob;
import com.mset.quartz.jobs.StartJob;
import com.mset.quartz.jobs.StopJob;

import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;

import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ProgramaSchedule{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramaSchedule.class);
    //private ServletConfig config;
    private Scheduler scheduler;
    private Scheduler schedulerCRON;
 
    public void init(ServletContext context){
        try {
        	if(context==null){
        		scheduler = StdSchedulerFactory.getDefaultScheduler();                
        	}	
        	else
        	{
	        	StdSchedulerFactory factory = (StdSchedulerFactory) context
	                    .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);        	
	            scheduler = factory.getScheduler();
        	}

            Properties props = new Properties();
            props.put("org.quartz.scheduler.instanceName", "DefaultQuartzScheduler");
            props.put("org.quartz.scheduler.rmi.export", "false");
            props.put("org.quartz.scheduler.rmi.proxy", "false");
            props.put("org.quartz.scheduler.wrapJobExecutionInUserTransaction", "false");
            props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
            props.put("org.quartz.threadPool.threadCount", "10");
            props.put("org.quartz.threadPool.threadPriority", "5");
            props.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
            props.put("org.quartz.jobStore.misfireThreshold", "60000");
            props.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
            StdSchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            schedFact.initialize(props);    
            schedulerCRON=schedFact.getScheduler();        	
        	
            scheduler.start();
            schedulerCRON.start();
        	
        } catch (SchedulerException e) {
        	System.out.println("Init Error: "+e);
        }
        System.out.println("Job was scheduled se inicializo ");
    }

    public void finish(){
    	try{
    		scheduler.shutdown();
    		schedulerCRON.shutdown();
    		System.out.println("Job was scheduled se termino");
    	}
    	catch(SchedulerException e){
    		System.out.println("Finish Error: "+e);
    	}
    }    
    
    public void programar(ArrayList<ProgramaVO> listado){
		for(int i=0;i<listado.size();i++){			
			ProgramaVO p= listado.get(i);
			String canal=p.getCanal();
			String fechaInicio=p.getFechaInicio();
			String horaInicio=p.getHoraInicio();
			String fechaFin=p.getFechaFin();
			String horaFin=p.getHoraFin();
			String group=canal+"."+fechaInicio;
			String jobStart="Start."+fechaInicio+"--"+horaInicio;
			String jobEnd="Stop."+fechaFin+"--"+horaFin;
			programarCIRES("Start",group,jobStart,fechaInicio+" "+horaInicio);
			programarCIRES("Stop",group,jobEnd,fechaFin+" "+horaFin);
			programarMetadatos(group, jobStart, fechaInicio+" "+horaInicio, fechaFin+" "+horaFin, "1");
		}    	
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
    
    public void programarCIRES(String evento, String groupName, String jobName, String date){
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
    public void eliminarGrupo(String groupName){
    	try{
	    	GroupMatcher.jobGroupEquals(Scheduler.DEFAULT_GROUP);
	    	scheduler.deleteJobs(new ArrayList<JobKey>(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))));
    	}catch(Exception e){
	    	System.out.println("Error deleting ");
	    }
    }  

    
    public void programarMetadatos(String groupName, String jobName, String inicio, String fin, String everySeconds){
    	String cron="0/"+everySeconds+" * * * * ?";
    	cron(groupName,jobName,inicio,fin,cron);
    }
    
    public void cron(String groupName, String jobName,String inicio, String fin, String cron){
        try {
            JobKey jobKey = JobKey.jobKey(jobName, "META-"+groupName);
            JobDetail job= newJob(MetadatosJob.class).withIdentity(jobKey).requestRecovery().build();
 
            String triggerName = String.format("trigger-%s", jobKey);
            Trigger simpleTrigger = newTrigger()
                    .withIdentity(triggerName, groupName)
                    .startAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inicio))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .endAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fin))
                    .build();
 
            boolean jobExists = scheduler.checkExists(job.getKey());
            if (!jobExists) {
            	schedulerCRON.scheduleJob(job, simpleTrigger);
            } else {
            	schedulerCRON.rescheduleJob(simpleTrigger.getKey(), simpleTrigger);
            }
        } catch (Exception e) {
        	System.out.println("Metadatos was not scheduled: "+e);
            LOGGER.error("Metadatos was not scheduled", e);
        }
        System.out.println("Metadatos was scheduled: ");
    }    
}