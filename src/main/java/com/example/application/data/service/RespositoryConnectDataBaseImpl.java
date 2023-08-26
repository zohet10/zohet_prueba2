package com.example.application.data.service;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

import com.example.application.data.entity.Especies;
import com.example.application.data.entity.ResponseEspecies;
import com.example.application.data.entity.ResponseHabitats;

import okhttp3.ResponseBody;


public class RespositoryConnectDataBaseImpl {
	private static RespositoryConnectDataBaseImpl instance;
	private ClientRepository client;
	
	private RespositoryConnectDataBaseImpl (String url, Long timeout) {
		System.out.println(url);
		this.client = new ClientRepository(url, timeout);
	}
	
	public static RespositoryConnectDataBaseImpl getInstance(String url, Long timeout) {
		if(instance == null) {
			synchronized (RespositoryConnectDataBaseImpl.class) {
				instance = new RespositoryConnectDataBaseImpl(url, timeout);
			}
		}
		return instance;
	}
	
	public ResponseEspecies getEspecies() throws IOException{
		Call<ResponseEspecies> call=  client.getDataBase().getEspecies();
		Response<ResponseEspecies> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean setEspecie(Especies nueva) throws IOException{
		Call<ResponseBody> call = client.getDataBase().setEspecie(nueva);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	
	public boolean updateEspecie(Especies actualizar) throws IOException{
		Call<ResponseBody> call = client.getDataBase().updateEspecie(actualizar);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean deleteEspecie(int id) throws IOException{
		Call<ResponseBody> call = client.getDataBase().deleteEspecie(id);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public ResponseHabitats getHabitats() throws IOException{
		Call<ResponseHabitats> call = client.getDataBase().getHabitats();
		Response<ResponseHabitats> response = call.execute();
		if(response.isSuccessful()) {
			return response.body();
		}else {
			return null;
		}
	}
}
