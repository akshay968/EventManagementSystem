package com.backend.EventManagementSystem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    	 Event save=this.eventRespository.save(event);
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
    public ResponseEntity<?>  find(@RequestParam String startDate,@RequestParam Double latitude,@RequestParam Double longitude,@RequestParam(required = false, defaultValue = "0") int page,@RequestParam(required=false, defaultValue = "10") int size) {
        if (startDate == null || latitude == null || longitude == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required parameters missing");
        }
         Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
        	System.out.println("sffasf");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid date format. Please use yyyy-MM-dd format");
        }
        if (!isValidLatitude(latitude) || !isValidLongitude(longitude))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Invalid latitude or longitude values");
        
    	return  ResponseEntity.ok(eventService.getResultEvents(startDate,latitude,longitude,page,size));
    }

	@GetMapping("weather")
	public ResponseEntity<?>  getweather(@RequestParam String city,@RequestParam String date){
		 Date date1;
		try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid date format. Please use yyyy-MM-dd format");
        }
		return  ResponseEntity.ok(weatherService.getWeatherConditions(city,date));

	}
	@GetMapping("distance")
	public ResponseEntity<?> getDistance( @RequestParam("latitude1") double latitude1,
            @RequestParam("longitude1") double longitude1,
            @RequestParam("latitude2") double latitude2,
            @RequestParam("longitude2") double longitude2){
                if (!isValidLatitude(latitude1) || !isValidLongitude(longitude1) ||
                !isValidLatitude(latitude2) || !isValidLongitude(longitude2)) {
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Invalid latitude or longitude values");
            }
		return  ResponseEntity.ok(distanceService.getDistance(latitude1,longitude1,latitude2,longitude2));

	}


    private boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

   
    private boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

}
