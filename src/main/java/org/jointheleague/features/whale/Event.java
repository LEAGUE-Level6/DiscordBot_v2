package org.jointheleague.features.whale;

import java.util.ArrayList;

import org.javacord.api.entity.user.User;

public class Event {
	private String name;
	private Time time;
	private Time endTime;
	private String date;
	private ArrayList<Person> people;
	private boolean isLive; 
	private boolean ReminderSent; 
	private String iconUrl;
	
	Event(String name, Time time, String date) {
		this.name = name;
		this.time = time;
		this.date = date;
		people = new ArrayList<Person>();
		setLive(false);
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
	public ArrayList<Person> getPeople() {
		return people;
	}
	public void addPeople(Person people) {
		this.people.add(people);
	}
	public void removePeople(User people) {
		this.people.remove(people);
	}
	public void removePeople(int index) {
		this.people.remove(index);
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public boolean isReminderSent() {
		return ReminderSent;
	}

	public void setReminderSent(boolean hasReminderBeenSent) {
		this.ReminderSent = hasReminderBeenSent;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}
