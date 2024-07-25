package org.jointheleague.features.whale;

public class Time {
	private String hour;
	private String min;
	private Boolean isPm;
	private String timeZone;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
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

	public String get24Hour() {
		if (isPm == true) {
			String time = (Integer.parseInt(hour) + 12) + "";
			if (time.length() == 1) {
				time = "0" + time;
			}
			return time;
		} else {
			return hour;
		}
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
			return hour + ":" + min + "pm";
		} else {
			return hour + ":" + min + "am";
		}
	}

}
