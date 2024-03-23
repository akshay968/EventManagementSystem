package com.backend.EventManagementSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DistanceService {
	@Value("${distance.api.url}")
	private String distanceApiUrl;
	@Value("${distance.api.key}")
	private String distanceApiKey;
	
	public DistanceService() {
		super();
	}

	public Double getDistance(double latitude1, double longitude1,double latitude2,double longitude2) {
		
		RestTemplate  restTemplate= new RestTemplate();
        String apiUrl = String.format("%s?code=%s&latitude1=%f&longitude1=%f&latitude2=%f&longitude2=%f", distanceApiUrl, distanceApiKey, latitude1, longitude1,latitude2,longitude1);
		String result = restTemplate.getForObject(apiUrl,String.class);
		try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(result);

            String distanceStr = jsonNode.get("distance").asText();
            return Double.parseDouble(distanceStr);
            
        } catch (Exception e) {
            e.printStackTrace(); 
           
        }
		 return -1.0;
       
	}
	}

