package com.example.application.data.controller;

import java.io.IOException;

import com.example.application.data.entity.Especies;
import com.example.application.data.entity.ResponseEspecies;
import com.example.application.data.entity.ResponseHabitats;
import com.example.application.data.service.RespositoryConnectDataBaseImpl;
import com.example.application.views.recuperacion202210040039.Recuperacion202210040039View;

public class EspeciesInteractorImpl implements EspeciesInteractor{
	private RespositoryConnectDataBaseImpl modelo;
	private Recuperacion202210040039View vista;
	
	public EspeciesInteractorImpl(Recuperacion202210040039View vista) {
		super();
		this.modelo = RespositoryConnectDataBaseImpl.getInstance("https://apex.oracle.com/", 6000000L);
		this.vista = vista;
	}
	
	@Override
	public void consultarEspecies() {
		try {
			ResponseEspecies response = this.modelo.getEspecies();
			this.vista.refrescarGridEspecies(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createEspecie(Especies nuevo) {
		try {
			boolean response = this.modelo.setEspecie(nuevo);
			this.vista.statusMsgCreateEspecie(response);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateEspecie(Especies nuevo) {
		try {
			boolean response = this.modelo.updateEspecie(nuevo);
			this.vista.statusMsgUpdateEspecie(response);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEspecie(int id) {
		try {
			boolean response = this.modelo.deleteEspecie(id);
			this.vista.statusMsgDeleteEspecie(response);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void consultarHabitas() {
		try {
			ResponseHabitats response = this.modelo.getHabitats();
			this.vista.refrescarHabitats(response.getItems());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


}
