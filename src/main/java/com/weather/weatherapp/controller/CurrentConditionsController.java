package com.weather.weatherapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.weatherapp.dto.currentConditions.CurrentConditionsDTO;
import com.weather.weatherapp.service.ICurrentConditionsService;

@Controller
@RequestMapping(value = "/conditions")
public class CurrentConditionsController {
	
	@Autowired
	ICurrentConditionsService currentConditionsService;
	
	@GetMapping(value ="/current")
	public ResponseEntity<CurrentConditionsDTO> getCurrentConditions(@RequestParam String apikey,@RequestParam String cityId) throws IOException, InterruptedException{
		
		return currentConditionsService.getCurrentConditions(apikey, cityId);
		
	}

}
