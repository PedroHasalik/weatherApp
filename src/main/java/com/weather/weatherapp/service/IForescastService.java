package com.weather.weatherapp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.weather.weatherapp.domain.DailyForecast;
import com.weather.weatherapp.domain.HourlyForecast;
import com.weather.weatherapp.dto.forecast.ForecastDTO;
import com.weather.weatherapp.dto.hourlyForecast.HourlyForecastDTO;

public interface IForescastService {
	
	public ResponseEntity<ForecastDTO> getDailyForecast(String apikey, String cityId) throws IOException, InterruptedException;
	
	public ResponseEntity<HourlyForecastDTO> getHourlyForecast(String apikey, String cityId) throws IOException, InterruptedException;
	
	public DailyForecast getDailyForescastByID(Long id);
	
	public HourlyForecast getHourlyForecastByID(Long id);

}
