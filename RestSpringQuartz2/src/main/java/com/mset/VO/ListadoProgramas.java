package com.mset.VO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ListadoProgramas {
	private static final List<ProgramaVO> PROGRAMAS = new ArrayList<ProgramaVO>();
	//@Autowired private String implementaString;
	static {
		PROGRAMAS.add(new ProgramaVO("telecinco","001","001","0","Gran Hermano","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("telecinco","001","002","0","Noticias5","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","003","0","Salvame","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","004","0","Noticias4","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","005","0","Cambiame","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("divinity","003","006","0","MHV","2016-03-08","15:56:00","2016-03-08","15:56:00"));
		PROGRAMAS.add(new ProgramaVO("divinity","003","007","0","NoticiasDiv","2016-03-08","15:56:00","2016-03-08","15:56:00"));
	}
	public static List<ProgramaVO> getProgramas(){
		
		//System.out.println("implementaString: "+implementaString);
		return PROGRAMAS;
	}
	public static void addPrograma(ProgramaVO p){
		PROGRAMAS.add(p);
	}
}
