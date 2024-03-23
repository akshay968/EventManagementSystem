package com.backend.EventManagementSystem.entities;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import org.springframework.data.annotation.Id; 

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document(collection="events")
@Data
public class Event {
//	@Id
//	private String id;
	private String event_name;
	private String city_name;
	private String date;
	private String time;
	private Double latitude;
	private Double longitude;
	
	public Event(String event_name, String city_name, String date, String time, Double latitude, Double longitude) {
		super();
		
		this.event_name = event_name;
		this.city_name = city_name;
		this.date = date;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
}
