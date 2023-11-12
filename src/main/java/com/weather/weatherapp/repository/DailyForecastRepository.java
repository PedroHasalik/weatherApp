package com.weather.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.weatherapp.domain.DailyForecast;

public interface DailyForecastRepository extends JpaRepository<DailyForecast,Long> {

}
