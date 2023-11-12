package com.weather.weatherapp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.weather.weatherapp.domain.City;
import com.weather.weatherapp.utils.Response;


public interface IlocationService {
	
	public  ResponseEntity<Response> getLocation(String apiKey, String city) throws IOException, InterruptedException;
	
	public City getCityByID(Long id);
	
	public void deleteCityByID(Long id);

}
