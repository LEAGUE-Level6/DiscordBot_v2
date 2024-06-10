package org.jointheleague.features.whale;

public class Time {
	String hour;
	String min;
	Boolean isPm;
	String[] timeZone;
	
	public String[] getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String[] timeZone) {
		this.timeZone = timeZone;
	}

	Time(String hour, String min) {
		this.hour = hour;
		this.min = min;
		isPm = true;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public Boolean getIsPm() {
		return isPm;
	}

	public void setIsPm(Boolean isPm) {
		this.isPm = isPm;
	}
	public String getTimeAsString() {
		if (isPm) {
			return hour+":"+min+"pm";
		}
		else {
			return hour+":"+min+"am";
		}
	}
	
}
