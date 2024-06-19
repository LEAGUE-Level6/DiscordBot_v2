package org.jointheleague.features.whale;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Timezones {
     Map<String, Integer> usTimezoneMap;

    public Timezones() {
        initializeTimezoneMap();
        displayCurrentTime("America/Los_Angeles");
    }

    private void initializeTimezoneMap() {
        usTimezoneMap = new HashMap<>();
        // US Timezones relative to Pacific Time (PT)
        usTimezoneMap.put("PST", 0);
        usTimezoneMap.put("PT", 0);
        usTimezoneMap.put("PDT", 0); 
        usTimezoneMap.put("MST", +1);
        usTimezoneMap.put("MT", +1);
        usTimezoneMap.put("MDT", +1);
        usTimezoneMap.put("CST", +2); 
        usTimezoneMap.put("CT", +2);
        usTimezoneMap.put("CDT", +2); 
        usTimezoneMap.put("EST", +3);
        usTimezoneMap.put("ET", +3);
        usTimezoneMap.put("EDT", +3); 
    }

    public void addTimezone(String abbreviation, int differenceFromPT) {
        usTimezoneMap.put(abbreviation.toUpperCase(), differenceFromPT);
    }

    public void displayCurrentTime(String timezoneId) {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(timezoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = currentTime.format(formatter);
        System.out.println("Current time in " + timezoneId + ": " + formattedTime);
    }
}
