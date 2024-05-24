package org.jointheleague.features.whale;

public class Event {
	private String name;
	private String time;
	private String zone;
	private String date;
	private String[] people;
	
	Event(String name, String time) {
		this.name = name;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String[] getPeople() {
		return people;
	}
	public void setPeople(String[] people) {
		this.people = people;
	}
	
}
