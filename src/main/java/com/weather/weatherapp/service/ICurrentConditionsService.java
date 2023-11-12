package com.weather.weatherapp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.weather.weatherapp.domain.CurrentConditions;
import com.weather.weatherapp.utils.Response;

public interface ICurrentConditionsService {
	
	public ResponseEntity<Response> getCurrentConditions(String apikey, String cityId) throws IOException, InterruptedException;
	
	public CurrentConditions getCurrentConditionByID(Long id);
}
