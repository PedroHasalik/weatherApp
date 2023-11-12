package com.weather.weatherapp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.weather.weatherapp.domain.DailyForecast;
import com.weather.weatherapp.domain.HourlyForecast;
import com.weather.weatherapp.utils.Response;

public interface IForescastService {
	
	public ResponseEntity<Response> getDailyForecast(String apikey, String cityId) throws IOException, InterruptedException;
	
	public ResponseEntity<Response> getHourlyForecast(String apikey, String cityId) throws IOException, InterruptedException;
	
	public DailyForecast getDailyForescastByID(Long id);
	
	public HourlyForecast getHourlyForecastByID(Long id);
	
	public void deleteDailyForecastByID(Long id);
	
	public void deleteHourlyForecastByID(Long id);

}
