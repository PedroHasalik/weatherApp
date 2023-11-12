package com.weather.weatherapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.weather.weatherapp.dto.city.CityDTO;


public interface IlocationService {
	
	public  ResponseEntity<List<CityDTO>> getLocation(String apiKey, String city) throws IOException, InterruptedException;

}
