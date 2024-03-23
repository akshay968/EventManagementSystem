package com.backend.EventManagementSystem.entities;

import java.time.LocalDate;

import lombok.Data;
@Data
public class Results {
	private String event_name;
	private String city_name;
    private String date;
    private String weather;
    private double distance_km;
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
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public double getDistance_km() {
		return distance_km;
	}
	public void setDistance_km(double distance_km) {
		this.distance_km = distance_km;
	}
	public Results(Event event, String weather, double distance_km) {
		super();
		this.event_name = event.getEvent_name();
		this.city_name = event.getCity_name();
		this.date = event.getDate();
		this.weather = weather;
		this.distance_km = distance_km;
	}
	public Results() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
}
