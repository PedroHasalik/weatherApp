package com.weather.weatherapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather.weatherapp.domain.CurrentConditions;

@Repository
public interface CurrentConditionsRepository extends JpaRepository<CurrentConditions,Long> {

}
