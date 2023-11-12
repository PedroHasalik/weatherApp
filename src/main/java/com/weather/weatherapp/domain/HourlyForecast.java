package com.weather.weatherapp.domain;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyForecast {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String dateTime;
	
	@Column
	private int weatherIcon;
	
	@Column
	private String iconPhrase;
	
	@Column
	private double temperature;
	
	@Column
	private String temperatureUnit;
	

}
