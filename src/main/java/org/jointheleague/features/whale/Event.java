package org.jointheleague.features.whale;

import org.javacord.api.entity.user.User;

public class Event {
	private String name;
	private Time time;
	private String date;
	private User[] people;
	
	Event(String name, Time time, String date) {
		this.name = name;
		this.time = time;
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public User[] getPeople() {
		return people;
	}
	public void setPeople(User[] people) {
		this.people = people;
	}
	
}
