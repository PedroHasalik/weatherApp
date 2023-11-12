package com.weather.weatherapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.weatherapp.service.IForescastService;
import com.weather.weatherapp.utils.ObtTimeStamp;
import com.weather.weatherapp.utils.Response;

@Controller
@RequestMapping(value = "/forecast")
public class ForecastController {
	
	@Autowired
	IForescastService forescastService;
	
	@GetMapping(value ="/daily")
	public ResponseEntity<Response> getDailyForecast(@RequestParam String apikey,@RequestParam String cityId) throws IOException, InterruptedException {
		
		if(apikey == null) {
			Response response = new Response(); 
			
			response.setResponseCode(400);
			response.setData("");
			response.setStatus("apiKey no puede ser un valor nulo");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(400));
			
			return responseEntity;
		}
		
		if(cityId == null) {
			Response response = new Response(); 
			
			response.setResponseCode(400);
			response.setData("");
			response.setStatus("cityName no puede ser un valor nulo");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(400));
			
			return responseEntity;
		}
		
		return forescastService.getDailyForecast(apikey, cityId);
		
	}
	
	@GetMapping(value ="/hourly")
	public ResponseEntity<Response> getHourlyForecast(@RequestParam String apikey,@RequestParam String cityId) throws IOException, InterruptedException {
		
		if(apikey == null) {
			Response response = new Response(); 
			
			response.setResponseCode(400);
			response.setData("");
			response.setStatus("apiKey no puede ser un valor nulo");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(400));
			
			return responseEntity;
		}
		
		if(cityId == null) {
			Response response = new Response(); 
			
			response.setResponseCode(400);
			response.setData("");
			response.setStatus("cityName no puede ser un valor nulo");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(400));
			
			return responseEntity;
		}
		
		return forescastService.getHourlyForecast(apikey, cityId);
			
	}
}
