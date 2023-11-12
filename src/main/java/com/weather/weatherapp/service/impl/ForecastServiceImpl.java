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

import com.weather.weatherapp.domain.DailyForecast;
import com.weather.weatherapp.domain.HourlyForecast;
import com.weather.weatherapp.dto.forecast.ForecastDTO;
import com.weather.weatherapp.dto.hourlyForecast.HourlyForecastDTO;
import com.weather.weatherapp.repository.DailyForecastRepository;
import com.weather.weatherapp.repository.HourlyForecastRepository;
import com.weather.weatherapp.service.IForescastService;
import com.weather.weatherapp.utils.JSONUtils;

@Service
public class ForecastServiceImpl implements IForescastService{
	
	@Value("${url.api.base}")
	private String baseUrl;
	
	@Autowired
	HourlyForecastRepository hourlyForecastRepository;
	
	@Autowired
	DailyForecastRepository dailyForecastRepository;
	
	private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	
	
	@Override
	public ResponseEntity<ForecastDTO> getDailyForecast(String apikey, String cityId) throws IOException, InterruptedException{
		
		ResponseEntity<ForecastDTO> responseEntity = null;
		
		String url = baseUrl + "/forecasts/v1/daily/1day/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 200) {
			
			ForecastDTO forecastDTO = new ForecastDTO();
			
			forecastDTO = JSONUtils.convertFromJsonToObject(response.body(),ForecastDTO.class);
			
			responseEntity = new ResponseEntity<ForecastDTO>(forecastDTO, HttpStatusCode.valueOf(200));
			
			this.saveDailyForecast(forecastDTO);
		}
			
		return responseEntity;
	}
	
	private void saveDailyForecast(ForecastDTO forecastDTO) {
		
		DailyForecast forecast = new DailyForecast();
		forecast.setCategory(forecastDTO.getHeadline().getCategory());
		forecast.setEffectiveDate(forecastDTO.getHeadline().getEffectiveDate());
		forecast.setEndDate(forecastDTO.getHeadline().getEndDate());
		forecast.setText(forecastDTO.getHeadline().getText());
		
		dailyForecastRepository.save(forecast);
	}
	
	@Override
	public ResponseEntity<HourlyForecastDTO> getHourlyForecast(String apikey, String cityId) throws IOException, InterruptedException{
		
		ResponseEntity<HourlyForecastDTO> responseEntity = null;
		
		String url = baseUrl + "/forecasts/v1/hourly/1hour/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 200) {
			
			HourlyForecastDTO hourlyForecastDTO = new HourlyForecastDTO();
			
			hourlyForecastDTO = JSONUtils.convertFromJsonToObject(response.body().substring(1 , response.body().length() - 1), HourlyForecastDTO.class);
			
			responseEntity = new ResponseEntity<HourlyForecastDTO>(hourlyForecastDTO, HttpStatusCode.valueOf(200));
			
			this.saveHourlyForecast(hourlyForecastDTO);
		}
		
		
		return responseEntity;
	}
	
	private void saveHourlyForecast(HourlyForecastDTO hourlyForecastDTO) {
		
		HourlyForecast hourlyForecast = new HourlyForecast();
		
		hourlyForecast.setDateTime(hourlyForecastDTO.getDateTime());
		hourlyForecast.setIconPhrase(hourlyForecastDTO.getIconPhrase());
		hourlyForecast.setTemperature(hourlyForecastDTO.getTemperature().getValue());
		hourlyForecast.setTemperatureUnit(hourlyForecastDTO.getTemperature().getUnit());
		hourlyForecast.setWeatherIcon(hourlyForecastDTO.getWeatherIcon());
		
		hourlyForecastRepository.save(hourlyForecast);
		
	}

}
