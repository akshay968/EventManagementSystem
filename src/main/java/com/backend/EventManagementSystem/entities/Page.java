package com.backend.EventManagementSystem.entities;

import java.util.List;

import lombok.Data;

@Data
public class Page {
private List<Results>events;
private int page;
private int pageSize;
private long totalEvents;
private int totalPages;


public Page(List<Results> events, int page, int pageSize, long totalEvents, int totalPages) {
	super();
	this.events = events;
	this.page = page;
	this.pageSize = pageSize;
	this.totalEvents = totalEvents;
	this.totalPages = totalPages;
}

public Page() {
	super();
}

public List<Results> getEvents() {
	return events;
}
public void setEvents(List<Results> events) {
	this.events = events;
}
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getPageSize() {
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public long getTotalEvents() {
	return totalEvents;
}
public void setTotalEvents(long totalEvents) {
	this.totalEvents = totalEvents;
}
public int getTotalPages() {
	return totalPages;
}
public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
}



}
