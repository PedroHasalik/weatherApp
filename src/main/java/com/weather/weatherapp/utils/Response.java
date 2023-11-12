package com.weather.weatherapp.utils;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
	
	private Timestamp timeStamp;
	private int responseCode;
	private String status;
	private Object data;

}
