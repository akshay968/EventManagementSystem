package com.backend.EventManagementSystem.service;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.backend.EventManagementSystem.entities.*;
import com.backend.EventManagementSystem.repository.EventRepository;

@Service
public class EventService {

	  
	  @Autowired
	 private  WeatherService weatherService=new WeatherService();
	  @Autowired
	 private  DistanceService distanceService=new DistanceService();
	  @Autowired
	   private final MongoTemplate mongoTemplate;
	  @Autowired
	    private EventRepository eventRespository;
	  	
		private List<Event> eventsList;
		private List<Event> resultEventsList;
		  @Autowired
		 public EventService(MongoTemplate mongoTemplate) {
			 this.mongoTemplate=mongoTemplate;
			 eventsList=new ArrayList();
			 resultEventsList=new ArrayList();
			  
	    
		 }
	  
	
		  public List<Event> getEvents(){
			  
			return  eventsList;
		}
	  
	  public void addEventsFromCsv(List<String[]> csvLines) {
	        // Assuming the CSV structure: eventName, cityName, date, time, latitude, longitude
	      int fg=1;
		  for (String[] line : csvLines) {
			  if(fg==1)
			  {
				  fg=0;
				  continue;
			  }
	            String eventName = line[0];
	            String cityName = line[1];
	            String date = line[2];
	            String time = line[3];
	            Double latitude = Double.parseDouble(line[4]);
	            Double longitude = Double.parseDouble(line[5]);

	            Event event = new Event(eventName, cityName, date, time, latitude, longitude);
	       	    Event save=this.eventRespository.save(event);

	        }
	        }
	        
	  public Page getResultEvents(String startDate,Double latitude,Double longitude,int page,int size){
		  Query query=new Query();
	         Date date1=null;
	       try {
		        date1=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       Calendar calendar = Calendar.getInstance();
	       calendar.setTime(date1);
	       calendar.add(Calendar.DAY_OF_MONTH, 14);
	       Date nextDate = calendar.getTime();
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	       String endDate = sdf.format(nextDate);
	       System.out.println(endDate);
	        Criteria criteria=new Criteria().where("date").gte(startDate).lte(endDate);
	        query.addCriteria(criteria);
	        query.with(Sort.by(Sort.Direction.ASC, "date"));

	        AggregationOperation matchOperation = Aggregation.match(criteria);
	        AggregationOperation countOperation = Aggregation.count().as("totalEvents");

	        Aggregation aggregation = Aggregation.newAggregation(matchOperation, countOperation);
	        Page result = mongoTemplate.aggregate(aggregation, "events", Page.class).getUniqueMappedResult();
	        Pageable pageable = PageRequest.of(page, size);
            query.with(pageable);
	       resultEventsList = mongoTemplate.find(query, Event.class);
	       List<Results>finalResults =new ArrayList(); 
	       
	       for(Event event:resultEventsList) {
	    	    
	    	   String weather=weatherService.getWeatherConditions(event.getCity_name(), event.getDate());
	    	   Double distance=distanceService.getDistance(latitude,longitude,event.getLatitude(),event.getLongitude());
	    	   Results res=new Results(event,weather,distance);
	    	   finalResults.add(res);
	       }
	       result.setEvents(finalResults);
	       result.setPage(page);
	       result.setPageSize(size);
	       int tpages=(int)result.getTotalEvents()/size;
	       if((result.getTotalEvents()%size!=0))
	    	   tpages++;
	       result.setTotalPages(tpages);


	       System.out.println(resultEventsList);
           
		  return result;
	  }

}
