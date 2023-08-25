package com.example.application.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Especies extends AbstractEntity {

    private String nombreCientifico;
    private String descripcion;
    private String habitad;
    private String cantidadDeEspecie;
    private String fechaRejistro;

    public String getNombreCientifico() {
        return nombreCientifico;
    }
    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getHabitad() {
        return habitad;
    }
    public void setHabitad(String habitad) {
        this.habitad = habitad;
    }
    public String getCantidadDeEspecie() {
        return cantidadDeEspecie;
    }
    public void setCantidadDeEspecie(String cantidadDeEspecie) {
        this.cantidadDeEspecie = cantidadDeEspecie;
    }
    public String getFechaRejistro() {
        return fechaRejistro;
    }
    public void setFechaRejistro(String fechaRejistro) {
        this.fechaRejistro = fechaRejistro;
    }

}
