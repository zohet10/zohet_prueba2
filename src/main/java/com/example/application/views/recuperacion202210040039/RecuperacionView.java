package com.example.application.views.recuperacion202210040039;

import java.util.List;

import com.example.application.data.entity.Especies;
import com.example.application.data.entity.Habitats;

public interface RecuperacionView {
	void refrescarGridEspecies(List<Especies> especies);
	void statusMsgCreateEspecie(boolean value);
	void statusMsgUpdateEspecie(boolean value);
	void statusMsgDeleteEspecie(boolean value);
	void refrescarHabitats(List<Habitats> habitats);
}
