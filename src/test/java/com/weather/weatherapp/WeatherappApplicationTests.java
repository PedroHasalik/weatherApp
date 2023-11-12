package com.weather.weatherapp;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.weather.weatherapp.domain.City;
import com.weather.weatherapp.domain.CurrentConditions;
import com.weather.weatherapp.domain.DailyForecast;
import com.weather.weatherapp.domain.HourlyForecast;
import com.weather.weatherapp.service.ICurrentConditionsService;
import com.weather.weatherapp.service.IForescastService;
import com.weather.weatherapp.service.IlocationService;


@SpringBootTest
class WeatherappApplicationTests {
	
	@Autowired
	IlocationService locationService;
	
	@Autowired
	IForescastService forescastService;
	
	@Autowired
	ICurrentConditionsService currentConditionsService;
	
	@Test
	void locationTest() {
		
		City city = locationService.getCityByID((long) 1);
		
		locationService.deleteCityByID((long) 1);
		
		assertEquals(city.getId(),(long) 1);
		
	}
	
	@Test
	void dailyForecastTest() {
		
		DailyForecast dailyForecast = forescastService.getDailyForescastByID((long) 1);
		
		forescastService.deleteDailyForecastByID((long) 1);
		
		assertEquals(dailyForecast.getId(),(long) 1);
	}
	
	@Test
	void hourlyForecastTest() {
		
		HourlyForecast hourlyForecast = forescastService.getHourlyForecastByID((long) 1);
		
		forescastService.deleteHourlyForecastByID((long) 1);
		
		assertEquals(hourlyForecast.getId(),(long) 1);
	}
	
	@Test
	void currentConditiosTest() {
		
		CurrentConditions currentConditions = currentConditionsService.getCurrentConditionByID((long) 1);
		
		currentConditionsService.deleteCurrentConditionByID((long) 1);
		
		assertEquals(currentConditions.getId(),(long) 1);
	}


	@Test
	void contextLoads() {
	}

}
