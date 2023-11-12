package com.weather.weatherapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.weatherapp.dto.city.CityDTO;
import com.weather.weatherapp.service.IlocationService;

@Controller
@RequestMapping(value = "/location")
public class LocationController {
	
	@Autowired
	private IlocationService locationService;
		
	@GetMapping(value ="/getcity")
	public ResponseEntity<List<CityDTO>> getLocation(@RequestParam String apikey, @RequestParam String cityName) throws IOException, InterruptedException {
		
		return locationService.getLocation(apikey, cityName);
				
	}

}
