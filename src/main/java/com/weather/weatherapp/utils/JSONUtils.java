package com.weather.weatherapp.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
	
	static public <T> List<T> convertFromJsonToList(String json, TypeReference<List<T>> var) throws JsonMappingException, JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, var);
	}
	
	static public <T> T convertFromJsonToObject(String json, Class<T> var) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, var);
	}
	
	static public String convertFromObjectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

}
