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
import com.weather.weatherapp.utils.ObtTimeStamp;
import com.weather.weatherapp.utils.Response;

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
	public ResponseEntity<Response> getDailyForecast(String apikey, String cityId) throws IOException, InterruptedException{
		
		Response response = new Response();
		
		ResponseEntity<Response> responseEntity = null;
		
		String url = baseUrl + "/forecasts/v1/daily/1day/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> responseHttp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(responseHttp.statusCode() == 200) {
			
			ForecastDTO forecastDTO = new ForecastDTO();
			
			forecastDTO = JSONUtils.convertFromJsonToObject(responseHttp.body(),ForecastDTO.class);
			
			response.setResponseCode(200);
			response.setData(forecastDTO);
			response.setStatus("Se obtuvo el pronostico del dia con exito");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(200));
			
			this.saveDailyForecast(forecastDTO);
		} else {
			
			response.setResponseCode(responseHttp.statusCode());
			response.setData("");
			response.setStatus("Error con la API externa");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(responseHttp.statusCode()));
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
	public ResponseEntity<Response> getHourlyForecast(String apikey, String cityId) throws IOException, InterruptedException{
		
		Response response = new Response();
		
		ResponseEntity<Response> responseEntity = null;
		
		String url = baseUrl + "/forecasts/v1/hourly/1hour/" + cityId  + "?apikey=" + apikey;
		
		HttpRequest httpRequest = HttpRequest.newBuilder()
											 .GET()
											 .uri(URI.create(url))
											 .build();
		
		HttpResponse<String> responseHttp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(responseHttp.statusCode() == 200) {
			
			HourlyForecastDTO hourlyForecastDTO = new HourlyForecastDTO();
			
			hourlyForecastDTO = JSONUtils.convertFromJsonToObject(responseHttp.body().substring(1 , responseHttp.body().length() - 1), HourlyForecastDTO.class);
			
			response.setResponseCode(200);
			response.setData(hourlyForecastDTO);
			response.setStatus("Se obtuvo el pronostico del la hora con exito");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(200));
			
			this.saveHourlyForecast(hourlyForecastDTO);
		} else {
			
			response.setResponseCode(responseHttp.statusCode());
			response.setData("");
			response.setStatus("Error con la API externa");
			response.setTimeStamp(ObtTimeStamp.getCurrentTimestamp());
			
			responseEntity = new ResponseEntity<Response>(response, HttpStatusCode.valueOf(responseHttp.statusCode()));
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
	
	@Override
	public DailyForecast getDailyForescastByID(Long id) {
		return dailyForecastRepository.getReferenceById(id);
	}
	
	@Override
	public HourlyForecast getHourlyForecastByID(Long id) {
		return hourlyForecastRepository.getReferenceById(id);
	}
	
	@Override
	public void deleteDailyForecastByID(Long id) {
		dailyForecastRepository.deleteById(id);
	}
	
	@Override
	public void deleteHourlyForecastByID(Long id) {
		hourlyForecastRepository.deleteById(id);
	}

}
