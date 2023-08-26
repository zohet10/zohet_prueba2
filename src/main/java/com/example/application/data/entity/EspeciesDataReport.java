package com.example.application.data.entity;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class EspeciesDataReport implements JRDataSource{

	private List<Especies> datos;
	private int counter = -1;
	private int maxCounter = 0;
	
	
	public void setData(List<Especies> value) {
		this.datos = value;
		this.maxCounter = this.datos.size() - 1;
	}

	public List<Especies> getEspecies(){
		return datos;
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getMaxCounter() {
		return maxCounter;
	}

	public void setMaxCounter(int maxCounter) {
		this.maxCounter = maxCounter;
	}

	@Override
	public boolean next() throws JRException {
		if(counter < maxCounter) {
			counter++;
			return true;
		}
		return false;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		if("ID".equals(jrField.getName())) {
			return datos.get(counter).getId();
		}else if("NOMBRE_CIENTIFICO".equals(jrField.getName())) {
			return datos.get(counter).getNombre_cientifico();
		}else if("DESCRIPCION".equals(jrField.getName())) {
			return datos.get(counter).getDescripcion();
		}else if("HABITAT".equals(jrField.getName())) {
			return datos.get(counter).getHabitat();
		}else if("EJEMPLARES".equals(jrField.getName())) {
			return datos.get(counter).getCantidad_ejemplares();
		}else if("FECHA_REGISTRO".equals(jrField.getName())) {
			return datos.get(counter).getFecha_registro();
		}
		return "";
	}
	

}
