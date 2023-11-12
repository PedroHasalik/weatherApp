package com.weather.weatherapp.repository;


import com.weather.weatherapp.domain.City;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long>{

}
