package com.backend.EventManagementSystem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.EventManagementSystem.entities.Event;
import com.backend.EventManagementSystem.entities.Page;
import com.backend.EventManagementSystem.entities.Results;
import com.backend.EventManagementSystem.repository.EventRepository;
import com.backend.EventManagementSystem.service.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@RestController
public class MyController {
    @Autowired
	private EventService eventService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private DistanceService distanceService;
   @Autowired
    private EventRepository eventRespository;
    
     @PostMapping("/addEvent")
     public ResponseEntity<?> addEvent(@RequestBody Event event){
    	 Event event1=new Event("Between thus table","Port Rebeccaberg","2024-03-01","18:00:00", 38.33354302,157.9579286);
    	 Event save=this.eventRespository.save(event1);

    	 return ResponseEntity.ok(save);
     }
     @PostMapping(value = "/events/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<String> addEventsFromCsv(@RequestParam("file") MultipartFile file)  {
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
             List<String[]> csvLines = new CSVReader(reader).readAll(); // Assuming you are using OpenCSV
             eventService.addEventsFromCsv(csvLines);
             return ResponseEntity.status(HttpStatus.CREATED).body("Events added successfully");
         } catch (IOException e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");
         } catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");

     }

     @GetMapping("/")
    public String  home() {
       
    	return "Welcome to EventManagement APIService";
    }
	@GetMapping("events/find")
    public Page  find(@RequestParam String startDate,@RequestParam Double latitude,@RequestParam Double longitude,@RequestParam(required = false, defaultValue = "0") int page,@RequestParam(required=false, defaultValue = "10") int size) {
       
    	return  eventService.getResultEvents(startDate,latitude,longitude,page,size);
    }

	@GetMapping("weather")
	public String getweather(@RequestParam String city,@RequestParam String date){
		return weatherService.getWeatherConditions(city,date);

	}
	@GetMapping("distance")
	public Double getDistance( @RequestParam("latitude1") double latitude1,
            @RequestParam("longitude1") double longitude1,
            @RequestParam("latitude2") double latitude2,
            @RequestParam("longitude2") double longitude2){
//		
		return distanceService.getDistance(latitude1,longitude1,latitude2,longitude2);

	}
//	@GetMapping("events/addEvent")
//	public void addEvent() {
//    	return ;
//    }
}
