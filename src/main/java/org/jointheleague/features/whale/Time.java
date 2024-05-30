package org.jointheleague.features.whale;

public class Time {
	int hour;
	int min;
	Boolean isPm;
	
	Time(int hour, int min) {
		this.hour = hour;
		this.min = min;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public Boolean getIsPm() {
		return isPm;
	}

	public void setIsPm(Boolean isPm) {
		this.isPm = isPm;
	}
	
}
