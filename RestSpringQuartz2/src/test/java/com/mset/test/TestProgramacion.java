package com.mset.test;

import java.util.List;

import com.mset.VO.ListadoProgramas;
import com.mset.VO.ProgramaVO;
import com.mset.quartz.schedules.ProgramaSchedule;

public class TestProgramacion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ProgramaVO> listado=ListadoProgramas.getProgramas();

		for(int i=0;i<listado.size();i++){
			ProgramaSchedule programaSchedule=new ProgramaSchedule();
			programaSchedule.init(null);					
			ProgramaVO p= listado.get(i);
			String canal=p.getCanal();
			String fechaInicio=p.getFechaInicio();
			String horaInicio=p.getHoraInicio();
			String fechaFin=p.getFechaFin();
			String horaFin=p.getHoraFin();
			String group=canal+"."+fechaInicio;
			String jobStart="Start."+fechaInicio+"--"+horaInicio;
			String jobEnd="Stop."+fechaFin+"--"+horaFin;
			programaSchedule.programar("Start",group,jobStart,fechaInicio+" "+horaInicio);
			programaSchedule.programar("Stop",group,jobEnd,fechaFin+" "+horaFin);
		}		
	}

}
