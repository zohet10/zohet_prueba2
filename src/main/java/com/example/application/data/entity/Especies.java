package com.example.application.data.entity;

public class Especies{
	
	private int id;
    private String nombre_cientifico;
    private String descripcion;
    private String habitat;
    private Double cantidad_ejemplares;
    private String fecha_registro;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre_cientifico() {
		return nombre_cientifico;
	}
	public void setNombre_cientifico(String nombre_cientifico) {
		this.nombre_cientifico = nombre_cientifico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getHabitat() {
		return habitat;
	}
	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}
	public Double getCantidad_ejemplares() {
		return cantidad_ejemplares;
	}
	public void setCantidad_ejemplares(Double cantidad_ejemplares) {
		this.cantidad_ejemplares = cantidad_ejemplares;
	}
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
    
    

}
