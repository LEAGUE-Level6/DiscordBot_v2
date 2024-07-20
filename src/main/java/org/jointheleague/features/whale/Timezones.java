package org.jointheleague.features.whale;

import java.nio.file.spi.FileSystemProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Timezones {
	Map<String, Double> usTimezoneMap;

	public Timezones() {
		initializeTimezoneMap();
		displayCurrentTime("America/Los_Angeles");
	}

	private void initializeTimezoneMap() {
		usTimezoneMap = new HashMap<>();
		// US Timezones relative to Pacific Time (PT)
		usTimezoneMap.put("PT", 0.0);
		usTimezoneMap.put("MT", +1.0);
		usTimezoneMap.put("CT", +2.0);
		usTimezoneMap.put("ET", +3.0);
	}

	public void addTimezone(String abbreviation, Double differenceFromPT) {
		usTimezoneMap.put(abbreviation.toUpperCase(), differenceFromPT);
	}

	public String[] getTimezones() {
		Object[] zones = usTimezoneMap.keySet().toArray();
		String[] zonesStrArr = new String[zones.length];
		for (int i = 0; i < usTimezoneMap.size(); i++) {
			zonesStrArr[i] = zones[i] + "";
		}
		return zonesStrArr;
	}

	public void displayCurrentTime(String timezoneId) {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(timezoneId));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm d/M/YY");
		String formattedTime = currentTime.format(formatter);
		System.out.println("Current time in " + timezoneId + ": " + formattedTime);

	}

	public String getCurrentTime(boolean getAMPM) {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter;
		if (getAMPM) {
			formatter = DateTimeFormatter.ofPattern("hh:mm:a");
		} else {
			formatter = DateTimeFormatter.ofPattern("HHmm");
		}
		String formattedTime = currentTime.format(formatter);
		return formattedTime;
	}

	public String getCurrentDate() {
		LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/YY");
		String formattedTime = currentTime.format(formatter);
		return formattedTime;
	}
}
