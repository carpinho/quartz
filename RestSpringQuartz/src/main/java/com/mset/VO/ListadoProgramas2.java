package com.mset.VO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ListadoProgramas2 {
	private static final List<ProgramaVO> PROGRAMAS = new ArrayList<ProgramaVO>();
	@Autowired private String implementaString;
	static {
		PROGRAMAS.add(new ProgramaVO("telecinco","001","001","0","Gran Hermano","2016-02-29","15:00","2016-02-29","15:10"));
		PROGRAMAS.add(new ProgramaVO("telecinco","001","002","0","Noticias5","2016-02-29","15:30","2016-02-29","15:30"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","003","0","Salvame","2016-02-29","16:00","2016-02-29","16:10"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","004","0","Noticias4","2016-02-29","16:30","2016-02-29","16:40"));
		PROGRAMAS.add(new ProgramaVO("cuatro","002","005","0","Cambiame","2016-02-29","17:00","2016-02-29","17:10"));
		PROGRAMAS.add(new ProgramaVO("divinity","003","006","0","MHV","2016-02-29","16:20","2016-02-29","16:30"));
		PROGRAMAS.add(new ProgramaVO("divinity","003","007","0","NoticiasDiv","2016-02-29","16:50","2016-02-29","17:00"));
	}
	public List<ProgramaVO> getProgramas(){
		
		System.out.println("implementaString: "+implementaString);
		return PROGRAMAS;
	}
	public void addPrograma(ProgramaVO p){
		PROGRAMAS.add(p);
	}
}
