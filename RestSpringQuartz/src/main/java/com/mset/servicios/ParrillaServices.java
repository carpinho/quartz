package com.mset.servicios;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.mset.VO.ListadoProgramas;
import com.mset.VO.ListadoProgramas2;
import com.mset.VO.ProgramaVO;
import com.mset.quartz.schedules.ProgramaSchedule;

@RestController
@RequestMapping(value = "/programacion")
public class ParrillaServices{	

	//private ServletContext context;
	
	//@Autowired
	//String implementaString;	

	@Autowired
	ProgramaSchedule programaSchedule;	

	@RequestMapping(value="/eneas",method = RequestMethod.POST)
	public String EneasPOST(@RequestParam String xml) {
		System.out.println("XML de Eneas es:"+xml);
		return "OK Eneas";
	}	
	
	@RequestMapping(value="/isPost",method = RequestMethod.POST)
	public String programarPOST(@RequestParam String mills) {
		programaSchedule.programarMills("vic01Job","vic01Group",mills);
		return "OK POST";
	}
	
	@RequestMapping(value="/isGet", method = RequestMethod.GET)
	public String programarGET(@RequestParam String job, @RequestParam String group,  @RequestParam String mills) {
		//System.out.println(context.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY));
		System.out.println("Prueba precarga");
		programaSchedule.programarMills(job,group,mills);
		return "OK GET";
	}
	
	@RequestMapping(value="/getProgramas", method = RequestMethod.GET)
	public List<ProgramaVO> obtenerProgramas() {
		return ListadoProgramas.getProgramas();
	}	

	@RequestMapping(value="/getProgramas2", method = RequestMethod.GET)
	public List<ProgramaVO> obtenerProgramas2() {
		ListadoProgramas2 l= new ListadoProgramas2();
		return l.getProgramas();
	}
	
	@RequestMapping(value="/setPrograma", method = RequestMethod.POST)
	public ProgramaVO setPrograma(@RequestParam Map<String,String> requestParams) {
		System.out.println(requestParams);
		ProgramaVO p=new ProgramaVO();
		p.setCanal(requestParams.get("canal"));
		p.setCodParrilla(requestParams.get("codParrilla"));
		p.setFechaFin(requestParams.get("fechaFin"));
		p.setFechaInicio(requestParams.get("fechaInicio"));
		p.setGeo(requestParams.get("geo"));
		p.setHoraFin(requestParams.get("horaFin"));
		p.setHoraInicio(requestParams.get("horaInicio"));
		p.setNombre(requestParams.get("nombre"));
		p.setProducto(requestParams.get("producto"));
		ListadoProgramas.addPrograma(p);
		return p;
	}		

	@RequestMapping(value="/getPrograma", method = RequestMethod.GET)
	public ProgramaVO getPrograma(@RequestParam Map<String,String> requestParams) {
		System.out.println(requestParams);
		ProgramaVO p=new ProgramaVO();
		p.setCanal(requestParams.get("canal"));
		p.setCodParrilla(requestParams.get("codParrilla"));
		p.setFechaFin(requestParams.get("fechaFin"));
		p.setFechaInicio(requestParams.get("fechaInicio"));
		p.setGeo(requestParams.get("geo"));
		p.setHoraFin(requestParams.get("horaFin"));
		p.setHoraInicio(requestParams.get("horaInicio"));
		p.setNombre(requestParams.get("nombre"));
		p.setProducto(requestParams.get("producto"));
		//ListadoProgramas.addPrograma(p);
		return p;
	}	
	
	   @RequestMapping(value="/query", method=RequestMethod.GET)
	   public void queryMethod(@RequestParam String id,
	            @RequestParam Map<String, String> queryParameters,
	            @RequestParam MultiValueMap<String, String> multiMap) {
	    System.out.println("id=" + id);
	        System.out.println(queryParameters);
	        System.out.println(multiMap);
	   }
	   
		@RequestMapping(value="/setPrograma2", method = RequestMethod.POST, consumes = "application/json")
		public ProgramaVO setPrograma2(@RequestParam Map<String,String> requestParams) {
			System.out.println(requestParams);
			ProgramaVO p=new ProgramaVO();
			p.setCanal(requestParams.get("canal"));
			p.setCodParrilla(requestParams.get("codParrilla"));
			p.setFechaFin(requestParams.get("fechaFin"));
			p.setFechaInicio(requestParams.get("fechaInicio"));
			p.setGeo(requestParams.get("geo"));
			p.setHoraFin(requestParams.get("horaFin"));
			p.setHoraInicio(requestParams.get("horaInicio"));
			p.setNombre(requestParams.get("nombre"));
			p.setProducto(requestParams.get("producto"));
			ListadoProgramas.addPrograma(p);
			return p;
		}		

		@RequestMapping(value="/programar", method = RequestMethod.POST)
		public String programar() {
			/*
			Group:
				Canal.fecha
				Tarea:
				Start.Fechahora/Stop.Fechahora*/
			List<ProgramaVO> listado=ListadoProgramas.getProgramas();
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
				programaSchedule.programarMetadatos(group, jobStart, fechaInicio+" "+horaInicio, fechaFin+" "+horaFin, "1");
			}
			//programaSchedule.finish();
			return "Programado";
		}
		
		@RequestMapping(value="/publicidad",method = RequestMethod.POST)
		public String publicidad(@RequestParam String canal, @RequestParam String descripcion) {
			if(!ListadoProgramas.getPublicidades().containsKey(canal)){
				return "No emitiendo";
			}
			ListadoProgramas.getPublicidades().get(canal).setDesc(descripcion);
			return "Publicidad insertada";
		}
}