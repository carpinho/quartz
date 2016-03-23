package com.mset.VO;

import java.io.Serializable;

public class ProgramaVO implements Serializable{
	private String canal="";
	private String codParrilla="";
	private String producto="";
	private String geo="";
	private String nombre="";
	private String fechaInicio="";
	private String fechaFin="";
	private String horaInicio="";
	private String horaFin="";
	
	
	public ProgramaVO(){}
	public ProgramaVO(String canal, String codParrilla, String producto, String geo, String nombre,
			String fechaInicio, String horaInicio, String fechaFin, String horaFin){
		
		this.canal=canal;
		this.codParrilla=codParrilla;
		this.producto=producto;
		this.geo=geo;
		this.nombre=nombre;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.horaInicio=horaInicio;
		this.horaFin=horaFin;		
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getCodParrilla() {
		return codParrilla;
	}
	public void setCodParrilla(String codParrilla) {
		this.codParrilla = codParrilla;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
