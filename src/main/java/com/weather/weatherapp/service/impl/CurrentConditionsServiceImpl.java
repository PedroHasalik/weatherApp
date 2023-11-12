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
import com.weather.weatherapp.utils.ObtTimeStamp;
import com.weather.weatherapp.utils.Response;

@Service
public class CurrentConditionsServiceImpl implements ICurrentConditionsService {
	
	@Value("${url.api.base}")
	private String baseUrl;
	
	@Autowired
	private CurrentConditionsRepository currentConditionsRepository;
	
	
	private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	@Override
	public ResponseEntity<Response> getCurrentConditions(String apikey, String cityId) throws IOException, InterruptedException {
		
		Response response = new Response();
		
		ResponseEntity<Response> responseEntity = null;
		
		String url = baseUrl + "/currentconditions/v1/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> responseHttp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(responseHttp.statusCode() == 200) {
			
			CurrentConditionsDTO currentConditions = new CurrentConditionsDTO();
			
			currentConditions = JSONUtils.convertFromJsonToObject(responseHttp.body().substring(1 , responseHttp.body().length() - 1), CurrentConditionsDTO.class);
			
			this.saveCurrentConditions(currentConditions);
			
			response.setResponseCode(200);
			response.setData(currentConditions);
			response.setStatus("Se obtuvieron las condiciones actuales con exito");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(200));
			
		} else {
			response.setResponseCode(responseHttp.statusCode());
			response.setData("");
			response.setStatus("Error con la API externa");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(responseHttp.statusCode()));
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
	
	@Override
	public CurrentConditions getCurrentConditionByID(Long id) {
		return currentConditionsRepository.getReferenceById(id);
	}
	
	@Override
	public void deleteCurrentConditionByID(Long id) {
		currentConditionsRepository.deleteById(id);
	}
}
