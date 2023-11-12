package com.weather.weatherapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.weatherapp.dto.forecast.ForecastDTO;
import com.weather.weatherapp.dto.hourlyForecast.HourlyForecastDTO;
import com.weather.weatherapp.service.IForescastService;

@Controller
@RequestMapping(value = "/forecast")
public class ForecastController {
	
	@Autowired
	IForescastService forescastService;
	
	@GetMapping(value ="/daily")
	public ResponseEntity<ForecastDTO> getDailyForecast(@RequestParam String apikey,@RequestParam String cityId) throws IOException, InterruptedException {
		
		return forescastService.getDailyForecast(apikey, cityId);
		
	}
	
	@GetMapping(value ="/hourly")
	public ResponseEntity<HourlyForecastDTO> getHourlyForecast(@RequestParam String apikey,@RequestParam String cityId) throws IOException, InterruptedException {
		
		return forescastService.getHourlyForecast(apikey, cityId);
			
	}
}
