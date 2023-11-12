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
public class CurrentConditions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private String localObservationDate;
	
	@Column
	private String weatherText;
	
	@Column
	private Double temperature;
	
	@Column
	private String temperatureUnit;

}
