package com.example.application.data.service;

import java.util.concurrent.TimeUnit;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;

public class ClientRepository {
	private Retrofit retrofit;
	private HttpLoggingInterceptor interceptor = null;
	
	public ClientRepository (String url, Long timeout) {
		this.interceptor = new HttpLoggingInterceptor();
		this.interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.connectTimeout(timeout, TimeUnit.MILLISECONDS)
				.writeTimeout(timeout, TimeUnit.MILLISECONDS)
				.readTimeout(timeout, TimeUnit.MILLISECONDS)
				.build();
		retrofit = new Retrofit.Builder()
				.client(client)
				.baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()))
				.build();
	}
	public RepositoryConnectDataBase getDataBase() {
		return retrofit.create(RepositoryConnectDataBase.class);
	}
}
