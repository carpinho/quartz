package com.mset.config;

import javax.servlet.ServletContext;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.mset.quartz.schedules.ProgramaSchedule;

@Configuration
public class ProgramacionConfig implements ServletContextAware{
	private ServletContext context;
	@Bean
	 public String implementaString() {
		System.out.println("ProgramacionConfig - implementaString");
		System.out.println("context.getAttribute -"+context.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY));
		System.out.println("Prueba Pre carga Config");
		return "Hola";
	 }
	
	@Bean
	public ProgramaSchedule programaSchedule(){
		System.out.println("ProgramaSchedule - Config");
		ProgramaSchedule p= new ProgramaSchedule();
		p.init(context);
		return p;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		System.out.println("ProgramacionConfig - setServletContext");
		this.context = servletContext;			
	}
}
