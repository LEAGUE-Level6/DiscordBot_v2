package org.jointheleague.features.whale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.json.simple.JSONObject;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

public class Schedule extends Feature {
	Timezones timeZones = new Timezones();
	DiscordApi api;
	ApiGetter get;
	Boolean AMRmode = false;
	public final String add = "!addevent";
	public final String remove = "!removeevent";
	public final String people = "!people";
	public final String schedule = "!schedule";
	public final String start = "!startevent";
	public final String end = "!endevent";
	public final String settings = "!settings";
	public final String poll = "!pollevent";
	public final String[] available = {"!ican" , "imavailable"};
	boolean areWeRemoving;
	ArrayList<Event> eventList = new ArrayList<Event>();

	public Schedule(String channelName, ApiGetter get) {
		super(channelName);
		this.get = get;

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed("Schedule Bot",
				"!addEvent: Adds an event. Start with the event name, time, and date ex. (!addEvent Valorant Grind 9:30pm pdt (tmr/fri/6/21)) \n\n"
						+ "!removeEvent: Removes an event\n\n"
						+ "!people: edit the people participating in an event \n\n"
						+ "!schedule: lists all events \n\n"
						+ "!startEvent: starts the nearest event early\n\n" 
						+ "!endEvent: ends the current event \n\n"
						+ "!pollEvent: ask users if they can make it to the event with a message and reactions\n\n"
						+ "!iCan/!imAvailable: allows you to choose what event(s) you can make it to \n\n"
						+ "!settings: configure the bot\n\n");

	}

	@Override
	public void handle(MessageCreateEvent discord) {
		api = get.getApi();
		String messageContent = discord.getMessageContent();
		// thsi will add the strings to the eventList
		//ADD
		//ADD
		//ADD
		//ADD
		if (messageContent.toLowerCase().startsWith(add) || messageContent.toLowerCase().startsWith("!addevnt")) {
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
				for (int i = 0; i < 10; i++) {
					if (eventString.contains(i + ":")) {
						indexOfTimes.add(eventString.indexOf(i + ":") + 1);
						System.out.println("A \':\' was found at index" + (eventString.indexOf(i + ":") + 1));
						isThereTime = true;
					}
				}
				if (isThereTime == false) {
					discord.getChannel().sendMessage("No time included!");
					discord.addReactionsToMessage("❌");
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

					for (int i = 0; i < timeZones.usTimezoneMap.size(); i++) {
						if (time.toUpperCase().contains(" " + timeZones.usTimezoneMap.keySet().toArray()[i])) {
							System.out.println("Found timezone");
							realTime.setTimeZone((String) timeZones.usTimezoneMap.keySet().toArray()[i]);
							System.out.println("realTime timezone " + realTime.getTimeZone());
							break;
						}
					}
				} else if (isANumber(eventString.charAt(indexOfTime - 1) + "")) {

					System.out.println("found at indexoftime -1");
					name = eventString.substring(0, indexOfTime - 2);
					time = eventString.substring(indexOfTime - 1, eventString.length()).trim();
					realTime = new Time(time.charAt(0) + "", time.charAt(2) + "" + time.charAt(3) + "");
					for (int i = 0; i < timeZones.usTimezoneMap.size(); i++) {
						if (time.toUpperCase().contains(" " + timeZones.usTimezoneMap.keySet().toArray()[i])) {
							System.out.println("Found timezone");
							realTime.setTimeZone((String) timeZones.usTimezoneMap.keySet().toArray()[i]);
							System.out.println("realTime timezone " + realTime.getTimeZone());
							break;
						}
					}

				} else {
					System.out.println("invalid time format");
					discord.getChannel().sendMessage("Invalid Format");
					discord.addReactionsToMessage("❌");
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
				} else if (date.toLowerCase().contains("/") || date.toLowerCase().contains("-")) {
					String[] fullDate;
					Date d = new Date();
					if (date.toLowerCase().contains("/")) {
						fullDate = date.split("/");
					} else if (date.toLowerCase().contains("-")) {
						fullDate = date.split("-");
					} else {
						discord.getChannel().sendMessage("Add a \"/\" or \"-\" between numbers in the date");
						discord.addReactionsToMessage("❌");
						fullDate = null;
					}
					if (fullDate.length == 2) {
						realDate = (fullDate[0] + "/" + fullDate[1] + "/"
								+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
					} else if (fullDate.length == 3) {
						realDate = (fullDate[0] + "/" + fullDate[1] + "/" + fullDate[2]);
					} else {
						discord.getChannel().sendMessage("Invalid Date");
						discord.addReactionsToMessage("❌");
					}
				} else {
					Date d = new Date();
					realDate = ((d.getMonth() + 1) + "/" + (d.getDate()) + "/"
							+ (d.getYear() + "").substring(1, (d.getYear() + "").length()));
				}

				Event event = new Event(name, realTime, realDate);
				eventList.add(event);
				System.out.println("Date = " + realDate);
				System.out.println("Timezone " + eventList.get(eventList.size() - 1).getTime().getTimeZone());
				System.out.println("event constructed");
				discord.addReactionsToMessage("✅");

			} else {
				discord.getChannel().sendMessage("Invalid Format");
				discord.addReactionsToMessage("❌");
			}

			discord.getChannel()
					.sendMessage("The string recived was Name = |" + eventList.get(eventList.size() - 1).getName()
							+ "| Time = |" + eventList.get(eventList.size() - 1).getTime().getTimeAsString()
							+ "| Zone = |" + eventList.get(eventList.size() - 1).getTime().getTimeZone() + "|"
							+ " |Date = |" + eventList.get(eventList.size() - 1).getDate() + "|");

			System.out.println("Message sent to channel");
			// event.getChannel().sendMessage("Sending a message to the channel");
		}
		// this will remove an event from the list
		//REMOVE
		//REMOVE
		//REMOVE
		//REMOVE
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
		 //SCHEDULE
		// SCHEDULE
		// SCHEDULE
		// SCHEDULE
		if (messageContent.toLowerCase().startsWith(schedule)) {
			String listOfEvents = "";
			for (int i = 0; i < eventList.size(); i++) {
				listOfEvents += eventList.get(i).getName() + " " + eventList.get(i).getTime().getTimeAsString() + " "
						+ eventList.get(i).getDate() + "\n";
			}

			discord.getChannel().sendMessage(listOfEvents);
		}

//		START EVENT
//		START EVENT
//		START EVENT
//		START EVENT
		if (messageContent.toLowerCase().startsWith(start)) {
			List<Event> closestEvents = new ArrayList<>();
			int[] closestDay = { Integer.MAX_VALUE, -1 };
			for (int i = 0; i < eventList.size(); i++) {
				String[] tempDate = eventList.get(i).getDate().split("/");
				int dateAsInt = Integer.parseInt(tempDate[2] + tempDate[0] + tempDate[1]
						+ (eventList.get(i).getTime().getHour()
								+ timeZones.usTimezoneMap.get(eventList.get(i).getTime().getTimeZone()))
						+ eventList.get(i).getTime().getMin());
				if (dateAsInt < closestDay[0]) {
					closestDay[0] = dateAsInt;
					closestDay[1] = i;
					closestEvents.clear(); // Clear previous closest events
					closestEvents.add(eventList.get(i));
				} else if (dateAsInt == closestDay[0]) {
					// If event falls on the same closest day, add it to the list
					closestEvents.add(eventList.get(i));
				}
			}
			System.out.println("Closest date is " + eventList.get(closestDay[1]).getDate() + " "
					+ eventList.get(closestDay[1]).getName());
			// If there are multiple events on the closest day, print them
			if (closestEvents.size() > 1) {
				System.out.println("Events on the closest day:");
				for (Event event : closestEvents) {
					System.out.println(event.getDate()); // or print event details as needed
				}
			}
		}
		//PEOPLE
		//PEOPLE
		//PEOPLE
		//PEOPLE
		if (messageContent.toLowerCase().startsWith(people)) {
			System.out.println("!people called");
			long serverId = 1240487344063385702L; 
			api.getServerById(serverId).ifPresent(server -> {
				System.out.println(server.getMemberCount());
				for (int i = 0; i < server.getMemberCount(); i++) {
					System.out.println(server.getMembers().toArray()[i]);
					server.getRolesByName(messageContent);
				}
			});
		}

		if (messageContent.toLowerCase().startsWith("!amrmode")) {
			AMRmode = true;
			discord.getChannel().sendMessage("AMR mode has be activated(you can disable it with !settings)");
			System.out.println("AMR mode on");
		}
//		SETTINGS
//		SETTINGS
//		SETTINGS
//		SETTINGS
		if (messageContent.toLowerCase().startsWith(settings)) {
			AMRmode = true;
			discord.getChannel().sendMessage("AMR mode has be activated(you can disable it with !settings)");
			System.out.println("AMR mode on");
		}

		if (messageContent.toLowerCase().startsWith("!test")) {
			EventConverter convert = new EventConverter();;
			System.out.println(timeZones.usTimezoneMap.keySet().toArray()[0]);
//			Date d = new Date();
//			for (int i = 0 ; i<20; i++) {
//				System.out.println((d.getMonth()+1) + "/" + d.getDate() + "/" + (d.getYear() + "").substring(1, (d.getYear() + "").length()) + "--" + d.getHours()+ ":" + d.getMinutes());
//				d.setHours(d.getHours()-1);
//			}

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

	public void sendDm(String userId, String message, MessageCreateEvent discord) {
		discord.getApi().getUserById(userId).thenAccept(user -> user.sendMessage(message));
	}

}
