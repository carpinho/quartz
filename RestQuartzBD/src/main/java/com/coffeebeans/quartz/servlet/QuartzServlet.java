package com.coffeebeans.quartz.servlet;
 
import com.coffeebeans.quartz.job.QuartzJob;
import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
 
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
 
/**
 * Created by muhamadto on 6/9/15.
 */
public class QuartzServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzServlet.class);
 
    private Scheduler scheduler;
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            StdSchedulerFactory factory = (StdSchedulerFactory) config.getServletContext()
                    .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
 
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            throw new ServletException();
        }
        super.init(config);
    }
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req,resp);
    }
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String jobName = req.getParameter("jobName");
            String groupName = req.getParameter("groupName");
 
            String mills = req.getParameter("mills");
 
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
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("Failed! Job was not saved. %s", e.getMessage()));
        }
        System.out.println("Job was scheduled: ");
    }
 
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}