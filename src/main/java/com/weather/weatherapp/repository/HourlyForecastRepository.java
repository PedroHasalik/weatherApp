package com.weather.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.weatherapp.domain.HourlyForecast;

public interface HourlyForecastRepository extends JpaRepository<HourlyForecast,Long>{

}
