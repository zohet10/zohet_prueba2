package com.example.application.data.service;

import com.example.application.data.entity.Especies;
import com.example.application.data.entity.ResponseEspecies;
import com.example.application.data.entity.ResponseHabitats;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Headers;

public interface RepositoryConnectDataBase {
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("pls/apex/aduana/ESPECIES/especies")
	Call<ResponseEspecies> getEspecies();

	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@POST("/pls/apex/aduana/ESPECIES/especies")
	Call<ResponseBody> setEspecie(@Body Especies especie);

	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@PUT("/pls/apex/aduana/ESPECIES/especies")
	Call<ResponseBody> updateEspecie(@Body Especies actualizar);
	
	@Headers({
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@DELETE("/pls/apex/aduana/ESPECIES/especies")
	Call<ResponseBody> deleteEspecie(@Query("id") int id);
	
	@Headers({
		"Content-Type: application/json",
		"Accept-Charset: utf-8",
		"User-Agent: Retrofit-Sample-App"
	})
	@GET("pls/apex/aduana/ESPECIES/habitats")
	Call<ResponseHabitats> getHabitats();
	
}
