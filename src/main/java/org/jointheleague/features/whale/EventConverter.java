package org.jointheleague.features.whale;

public class EventConverter {

    public static String convertToISO8601(Event event) {
        // Get date from Event object
        String date = event.getDate();

        // Get time from Event object
        Time time = event.getTime();
        String hour = time.getHour();
        String min = time.getMin();
        Boolean isPm = time.getIsPm();

        // Convert time to 24-hour format if it's in PM
        if (isPm) {
            int hourInt = Integer.parseInt(hour);
            if (hourInt != 12) {
                hourInt += 12;
                hour = String.valueOf(hourInt);
            }
        }

        // Construct ISO 8601 formatted date-time string
        StringBuilder iso8601 = new StringBuilder();
        iso8601.append(date).append("T").append(hour).append(":").append(min).append(":00");

        // Append timezone offset (assuming system default timezone)
        int offsetMinutes = java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()) / (60 * 1000);
        iso8601.append(offsetMinutes >= 0 ? "+" : "-");
        offsetMinutes = Math.abs(offsetMinutes);
        int hoursOffset = offsetMinutes / 60;
        int minutesOffset = offsetMinutes % 60;
        iso8601.append(String.format("%02d", hoursOffset)).append(":").append(String.format("%02d", minutesOffset));

        return iso8601.toString();
    }
}
