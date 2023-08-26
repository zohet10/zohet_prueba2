package com.example.application.data.controller;

import com.example.application.data.entity.Especies;

public interface EspeciesInteractor {
	void consultarEspecies();
	void createEspecie(Especies nuevo);
	void updateEspecie(Especies nuevo);
	void deleteEspecie(int id);
	void consultarHabitas();
}
