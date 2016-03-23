package com.mset.test;

import java.util.ArrayList;
import java.util.List;

import com.mset.VO.ListadoProgramas;
import com.mset.VO.ProgramaVO;
import com.mset.quartz.schedules.ProgramaSchedule;

public class TestProgramacion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		//programar();
		//eliminarGrupo("telecinco.2016-03-10");
		//eliminarGrupo("cuatro.2016-03-10");
		//eliminarGrupo("divinity.2016-03-10");
		//programarMetadatos();
		programarTodo();
	}
	public static void eliminarGrupo(String group){
		ProgramaSchedule programaSchedule=new ProgramaSchedule();
		programaSchedule.init(null);
		programaSchedule.eliminarGrupo(group);
		programaSchedule.finish();
	}
	
	public static void programarTodo(){
		ProgramaSchedule programaSchedule=new ProgramaSchedule();
		programaSchedule.init(null);
		programaSchedule.programar((ArrayList<ProgramaVO>) ListadoProgramas.getProgramas());
	}
	public static void programar(){
		// TODO Auto-generated method stub
		List<ProgramaVO> listado=ListadoProgramas.getProgramas();
		ProgramaSchedule programaSchedule=new ProgramaSchedule();
		programaSchedule.init(null);		
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
			programaSchedule.programarCIRES("Start",group,jobStart,fechaInicio+" "+horaInicio);
			programaSchedule.programarCIRES("Stop",group,jobEnd,fechaFin+" "+horaFin);
		}
		programaSchedule.finish();
	}
	
	public static void programarMetadatos(){
		//String inicio="2016-03-10 11:53:00";
		//String fin="2016-03-10 11:54:20";
		List<ProgramaVO> l = new ArrayList<ProgramaVO>();
		l.add(new ProgramaVO("telecinco","001","001","0","Gran Hermano","2016-03-10","12:40:00","2016-03-10","12:41:00"));
		ProgramaSchedule programaSchedule=new ProgramaSchedule();
		programaSchedule.init(null);
		for(int i=0;i<l.size();i++){			
			ProgramaVO p= l.get(i);
			String canal=p.getCanal();
			String fechaInicio=p.getFechaInicio();
			String horaInicio=p.getHoraInicio();
			String fechaFin=p.getFechaFin();
			String horaFin=p.getHoraFin();
			String group=canal+"."+fechaInicio;
			String jobStart="Start."+fechaInicio+"--"+horaInicio;
			String jobEnd="Stop."+fechaFin+"--"+horaFin;
			//programaSchedule.programarCIRES("Start",group,jobStart,fechaInicio+" "+horaInicio);
			programaSchedule.programarCIRES("Stop",group,jobEnd,fechaFin+" "+horaFin);
			programaSchedule.programarMetadatos(group, jobStart, fechaInicio+" "+horaInicio, fechaFin+" "+horaFin, "1");
		}		
		//programaSchedule.programar((ArrayList<ProgramaVO>)p);
		/*
		String everySeconds="1";
		ProgramaSchedule programaSchedule=new ProgramaSchedule();
		programaSchedule.init(null);
		programaSchedule.programarCIRES("Start","group","jobStart",inicio);
		programaSchedule.programarCIRES("Stop","group","jobEnd",fin);
		programaSchedule.programarMetadatos("pruebaGroupName", "pruebaJobName", inicio, fin, everySeconds);*/
		//programaSchedule.finish();
	}

}
