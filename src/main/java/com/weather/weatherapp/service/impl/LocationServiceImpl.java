package com.weather.weatherapp.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.weather.weatherapp.domain.City;
import com.weather.weatherapp.dto.city.CityDTO;
import com.weather.weatherapp.repository.CityRepository;
import com.weather.weatherapp.service.IlocationService;
import com.weather.weatherapp.utils.JSONUtils;



@Service
public class LocationServiceImpl implements IlocationService{
	
	@Value("${url.api.base}")
	private String baseUrl;
	
	@Autowired
	CityRepository cityRepository;
	
	private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	
	@Override
	public ResponseEntity<List<CityDTO>> getLocation(String apiKey, String city) throws IOException, InterruptedException {
		
		List<CityDTO> cities =new ArrayList<CityDTO>();
		
		ResponseEntity<List<CityDTO>> responseEntity = null;

		String url = baseUrl + "/locations/v1/cities/search?apikey=" + apiKey +"&q=" + city;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 200) {
			
			cities = JSONUtils.convertFromJsonToList(response.body(), new TypeReference<List<CityDTO>>() {});
			
			responseEntity = new ResponseEntity<List<CityDTO>>(cities, HttpStatusCode.valueOf(200));
			
			this.saveCity(cities);
		}
				
		return responseEntity;
		
	}
	
	private void saveCity (List<CityDTO> cities) {
		
		City city =  new City();
		city.setCityKey(cities.get(0).getKey());
		city.setCountry(cities.get(0).getCountry().getLocalizedName());
		city.setLocalizedName(cities.get(0).getLocalizedName());
		city.setType(cities.get(0).getType());
		
		cityRepository.save(city);
		
	}
	

}
