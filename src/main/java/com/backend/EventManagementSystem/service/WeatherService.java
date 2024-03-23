package com.backend.EventManagementSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {
	@Value("${weather.api.url}")
	private String weatherApiUrl;
	@Value("${weather.api.key}")
	private String weatherApiKey;
	
	public WeatherService() {
		super();
	}

	public String getWeatherConditions(String city,String date) {
		
		RestTemplate  restTemplate= new RestTemplate();
        String apiUrl = String.format("%s?code=%s&city=%s&date=%s", weatherApiUrl, weatherApiKey, city, date);
		String result = restTemplate.getForObject(apiUrl,String.class);
		try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(result);

            String weatherStr = jsonNode.get("weather").asText();
            return weatherStr ;
            
        } catch (Exception e) {
            e.printStackTrace(); 
           
        }
		return "-1";
	}
	}
