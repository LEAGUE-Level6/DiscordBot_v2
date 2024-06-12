package org.jointheleague.features.whale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Schedule extends Feature {
	TimezoneAbbreviations timeZones = new TimezoneAbbreviations();
	public final String add = "!addevent";
	public final String remove = "!removeevent";
	public final String people = "!addpeople";
	public final String schedule = "!schedule";
	public final String start = "!startevent";
	public final String end = "!endevent";
	public final String settings = "!settings";
	boolean areWeRemoving;
	ArrayList<Event> eventList = new ArrayList<Event>();

	public Schedule(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed("Schedule Bot",
				"!addEvent: Adds an event. Start with the event name, time, and date ex. (!addEvent Valorant Grind 9:30pm pdt (tmr/fri/6/21)) \n\n"
						+ "!removeEvent: Removes an event\n\n"
						+ "!people: edit the people participating in an event \n\n" + "!schedule: lists all events \n\n"
						+ "!startEvent: starts a chosen event early\n\n" + "!endEvent: ends the current event \n\n"
						+ "!settings: configure the bot\n");

	}

	@Override
	public void handle(MessageCreateEvent discord) {
		String messageContent = discord.getMessageContent();
		// thsi will add the strings to the eventList
		if (messageContent.toLowerCase().startsWith(add)) {
			System.out.println("!addEvent called");
			String eventString = messageContent.substring(add.length()).trim();
			if (eventString.contains(" ")) {
				System.out.println("message contains a space");
				String[] values = eventString.split(" ");
				String time = "";
				Time realTime;
				String name = "";
				String date = "";
				String realDate = "";

				// get tbe time/date from the rest of the string
				boolean isThereTime = false;
				ArrayList<Integer> indexOfTimes = new ArrayList<Integer>();
				for (int i = 0; i < 11; i++) {
					if (eventString.contains(i + ":")) {
						indexOfTimes.add(eventString.indexOf(i + ":") + 1);
						System.out.println("A \':\' was found at index" + (eventString.indexOf(i + ":") + 1));
						isThereTime = true;
					}
				}
				if (isThereTime == false) {
					discord.getChannel().sendMessage("No time included!");
					System.out.println("No time found");
				} else {
					System.out.println("A time was found ");
				}
				int indexOfTime = Integer.MAX_VALUE;
				for (int i = 0; i < indexOfTimes.size(); i++) {
					if (indexOfTimes.get(i) < indexOfTime) {
						indexOfTime = indexOfTimes.get(i);
						System.out.println("The index of the time was found(" + indexOfTime + ")");
					}
				}
				if (isANumber(eventString.charAt(indexOfTime - 2) + "")) {
					System.out.println("found at indexoftime -2");

					name = eventString.substring(0, indexOfTime - 3);
					time = eventString.substring(indexOfTime - 2, eventString.length()).trim();

					realTime = new Time(time.charAt(0) + "" + time.charAt(1) + "",
							time.charAt(3) + "" + time.charAt(4) + "");

					for (int i = 0; i < timeZones.timezoneList.size(); i++) {
						if (time.toLowerCase().contains(" " + timeZones.timezoneList.get(i)[0].toLowerCase() + " ")) {
							System.out.println("Found timezone");
							realTime.setTimeZone(timeZones.timezoneList.get(i));
							System.out.println("realTime timezone " + realTime.getTimeZone()[0]);
							break;
						}
					}
				} else if (isANumber(eventString.charAt(indexOfTime - 1) + "")) {

					System.out.println("found at indexoftime -1");
					name = eventString.substring(0, indexOfTime - 2);
					time = eventString.substring(indexOfTime - 1, eventString.length()).trim();
					realTime = new Time(time.charAt(0) + "", time.charAt(2) + "" + time.charAt(3) + "");

					for (int i = 0; i < timeZones.timezoneList.size(); i++) {
						if (time.toLowerCase().contains(" " + timeZones.timezoneList.get(i)[0].toLowerCase() + " ")) {
							System.out.println("Found timezone");
							realTime.setTimeZone(timeZones.timezoneList.get(i));
							System.out.println("realTime timezone " + realTime.getTimeZone()[0]);
							break;
						}
					}
				} else {
					System.out.println("invalid time format");
					discord.getChannel().sendMessage("Invalid Format");
					realTime = null;
				}
				if (time.toLowerCase().contains("am")) {
					realTime.setIsPm(false);
				} else if (time.toLowerCase().contains("pm")) {
					realTime.setIsPm(true);
				}
				System.out.println("time after prossesing is " + realTime.getHour() + " " + realTime.getMin());

				System.out.println("name and realtime values set");
				// get the date from the time var -Done
				// tmr will be the next day - Done
				// nothing will be same day or tmr depending on if the time is in the past
				// all days of the week(mon) will be the next day that is that day of the week
				// a maunual date (5/21) set that date in the same year
				String[] dateTime = time.split(" ");
				System.out.println("Time is " + time);
				date = dateTime[dateTime.length - 1];
				if (date.toLowerCase().contains("tmr")) {
					System.out.println("the string \"tmr\" was found");
					Date d = new Date();
					d.setMonth(d.getMonth());

					realDate = ((d.getMonth() + 1) + "/" + (d.getDate() + 1) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
					System.out.println("realDate set");
				} else if (date.toLowerCase().contains("mon") || date.toLowerCase().contains("monday")) {
					System.out.println("\'mon\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));
					if (d.getDay() == 1) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 1) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("tue") || date.toLowerCase().contains("tues")
						|| date.toLowerCase().contains("tuesday")) {
					System.out.println("\'tue\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 2) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 2) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("wed") || date.toLowerCase().contains("wednesday")) {
					System.out.println("\'tue\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 3) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 3) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("thurs") || date.toLowerCase().contains("thu")
						|| date.toLowerCase().contains("thur") || date.toLowerCase().contains("thursday")) {
					System.out.println("\'thurs\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 4) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 4) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("fri") || date.toLowerCase().contains("friday")) {
					System.out.println("\'fri\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 5) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 5) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("sat") || date.toLowerCase().contains("saturday")) {
					System.out.println("\'sat\' was found");
					Date d = new Date();
					int currentDate = d.getDate();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));
					if (d.getDay() == 6) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 6) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				} else if (date.toLowerCase().contains("sun") || date.toLowerCase().contains("sunday")) {
					System.out.println("\'sun\' was found");
					Date d = new Date();
					d.setMonth(d.getMonth());
					System.out.println("Current Date " + (d.getMonth() + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length())));

					if (d.getDay() == 0) {
						d.setDate(d.getDate() + 1);
						System.out.println("Same day detected and day added");
					}
					while (d.getDay() != 0) {
						d.setDate(d.getDate() + 1);
						System.out.println("day added " + d.getDate());
					}
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				}

				Event event = new Event(name, realTime, realDate);
				eventList.add(event);
				System.out.println("Date = " + realDate);
				System.out.println("Timezone " + eventList.get(eventList.size() - 1).getTime().getTimeZone()[0]);
				System.out.println("event constructed");

			} else {
				discord.getChannel().sendMessage("Invalid Format");
			}

			discord.getChannel()
					.sendMessage("The string recived was Name = |" + eventList.get(eventList.size() - 1).getName()
							+ "| Time = |" + eventList.get(eventList.size() - 1).getTime().getTimeAsString()
							+ "| Zone = |" + eventList.get(eventList.size() - 1).getTime().getTimeZone()[0] + " "
							+ eventList.get(eventList.size() - 1).getTime().getTimeZone()[1] + "|" + " |Date = |"
							+ eventList.get(eventList.size() - 1).getDate() + "|");

			System.out.println("Message sent to channel");
			// event.getChannel().sendMessage("Sending a message to the channel");
		}
		// this will remove an event from the list

		if (messageContent.toLowerCase().startsWith(remove) || messageContent.toLowerCase().startsWith("!removeevnt")) {
			areWeRemoving = true;
			String listOfEvents = "Enter the number next to the event to remove it sperate numbers with commas to remove multiple\n\n";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += (i + 1) + ": " + eventList.get(i).getName() + " "
						+ eventList.get(i).getTime().getTimeAsString() + " " + eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);

		} else if (areWeRemoving) {
			int index = -1;
			if (isANumber(discord.getMessageContent())) {
				System.out.println("No commas");
				index = Integer.parseInt(discord.getMessageContent());
				eventList.remove(index - 1);
				discord.getChannel().sendMessage("Event Removed");
				areWeRemoving = false;
			} else if (discord.getMessageContent().contains(",")) {
				System.out.println("Has commas");
				String[] indexesString = discord.getMessageContent().split(",");
				System.out.println("split");
				Integer[] indexes = new Integer[indexesString.length];
				System.out.println("int array made");
				int smallest = Integer.MAX_VALUE;
				for (int i = 0; i < indexesString.length; i++) {
					System.out.println("parseInt for " + indexesString[i]);
					indexes[i] = Integer.parseInt(indexesString[i]);
				}
				Event[] events = new Event[indexes.length];
				System.out.println("Event List created");
				for (int i = 0; i < events.length; i++) {
					events[i] = eventList.get(indexes[i] - 1);
					System.out.println("Setting" + (indexes[i] - 1));
				}
				for (int i = 0; i < events.length; i++) {
					eventList.remove(events[i]);
					System.out.println("removing " + events[i].getName());
				}
				areWeRemoving = false;
			}

		}

		System.out.println(areWeRemoving);

		if (messageContent.toLowerCase().startsWith(schedule)) {
			String listOfEvents = "";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += eventList.get(i).getName() + " " + eventList.get(i).getTime().getTimeAsString() + " "
						+ eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);
		}
		
		if (messageContent.toLowerCase().startsWith(start)) {
			String listOfEvents = "Enter the number next to the event to remove it sperate numbers with commas to remove multiple\n\n";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += (i + 1) + ": " + eventList.get(i).getName() + " "
						+ eventList.get(i).getTime().getTimeAsString() + " " + eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);
			int index = -1;
			if (isANumber(discord.getMessageContent())) {
				System.out.println("No commas");
				index = Integer.parseInt(discord.getMessageContent());
				eventList.remove(index - 1);
				discord.getChannel().sendMessage("Event Removed");
			}
			
		}
	}

	public static boolean isANumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
