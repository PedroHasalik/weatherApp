package com.weather.weatherapp.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.weather.weatherapp.domain.CurrentConditions;
import com.weather.weatherapp.dto.currentConditions.CurrentConditionsDTO;
import com.weather.weatherapp.repository.CurrentConditionsRepository;
import com.weather.weatherapp.service.ICurrentConditionsService;
import com.weather.weatherapp.utils.JSONUtils;

@Service
public class CurrentConditionsServiceImpl implements ICurrentConditionsService {
	
	@Value("${url.api.base}")
	private String baseUrl;
	
	@Autowired
	private CurrentConditionsRepository currentConditionsRepository;
	
	
	private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	@Override
	public ResponseEntity<CurrentConditionsDTO> getCurrentConditions(String apikey, String cityId) throws IOException, InterruptedException {
		
		ResponseEntity<CurrentConditionsDTO> responseEntity = null;
		
		String url = baseUrl + "/currentconditions/v1/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 200) {
			
			CurrentConditionsDTO currentConditions = new CurrentConditionsDTO();
			
			currentConditions = JSONUtils.convertFromJsonToObject(response.body().substring(1 , response.body().length() - 1), CurrentConditionsDTO.class);
			
			this.saveCurrentConditions(currentConditions);
			
			responseEntity = new ResponseEntity<CurrentConditionsDTO>(currentConditions, HttpStatusCode.valueOf(200));
			
		}
		
		
		return responseEntity;
		
	}
	
	private void saveCurrentConditions(CurrentConditionsDTO currentConditionsDTO) {
		
		CurrentConditions currentConditions = new CurrentConditions();
		
		currentConditions.setLocalObservationDate(currentConditionsDTO.getLocalObservationDateTime());
		currentConditions.setTemperature(currentConditionsDTO.getTemperature().getMetric().getValue());
		currentConditions.setTemperatureUnit(currentConditionsDTO.getTemperature().getMetric().getUnit());
		currentConditions.setWeatherText(currentConditionsDTO.getWeatherText());
		
		currentConditionsRepository.save(currentConditions);
		
	}
}
