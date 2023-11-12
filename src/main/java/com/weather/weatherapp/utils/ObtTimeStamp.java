package com.weather.weatherapp.utils;

import java.sql.Timestamp;
import java.time.Instant;

public class ObtTimeStamp {

	static public Timestamp  getCurrentTimestamp() {
		Instant intstant = Instant.now();
		return Timestamp.from(intstant);
	}
}
