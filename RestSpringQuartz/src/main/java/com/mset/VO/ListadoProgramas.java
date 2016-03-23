package com.mset.VO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class ListadoProgramas {
	private static final List<ProgramaVO> PROGRAMAS = new ArrayList<ProgramaVO>();
	private static final Map<String, PublicidadVO> PUBLICIDADES= new HashMap<String, PublicidadVO>();
	//@Autowired private String implementaString;
	static {
		PROGRAMAS.add(new ProgramaVO("telecinco","001","001","0","Gran Hermano","2016-03-11","13:15:00","2016-03-11","13:20:00"));
		//PROGRAMAS.add(new ProgramaVO("telecinco","001","002","0","Noticias5","2016-03-11","11:05:00","2016-03-11","11:06:00"));
		//PROGRAMAS.add(new ProgramaVO("cuatro","002","003","0","Salvame","2016-03-11","11:03:00","2016-03-11","11:05:00"));
		//PROGRAMAS.add(new ProgramaVO("cuatro","002","004","0","Noticias4","2016-03-11","11:06:00","2016-03-11","11:07:00"));
		//PROGRAMAS.add(new ProgramaVO("cuatro","002","005","0","Cambiame","2016-03-11","11:09:00","2016-03-11","11:10:00"));
		//PROGRAMAS.add(new ProgramaVO("divinity","003","006","0","MHV","2016-03-11","11:04:00","2016-03-11","11:06:00"));
		//PROGRAMAS.add(new ProgramaVO("divinity","003","007","0","NoticiasDiv","2016-03-11","11:07:00","2016-03-11","11:08:00"));
	}
	public static List<ProgramaVO> getProgramas(){
		
		//System.out.println("implementaString: "+implementaString);
		return PROGRAMAS;
	}
	public static void addPrograma(ProgramaVO p){
		PROGRAMAS.add(p);
	}
	public static void addPublicidad(PublicidadVO pu){
		PUBLICIDADES.put(pu.getCanal(), pu);
	}
	
	public static Map<String, PublicidadVO> getPublicidades(){
		
		//System.out.println("implementaString: "+implementaString);
		return PUBLICIDADES;
	}	
}
