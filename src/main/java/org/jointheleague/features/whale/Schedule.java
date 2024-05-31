package org.jointheleague.features.whale;

import java.util.ArrayList;
import java.util.Date;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Schedule extends Feature {

	public final String add = "!addEvent";
	public final String remove = "!removeEvent";
	public final String people = "!addPeople";
	public final String schedule = "!schedule";
	public final String start = "!startEvent";
	public final String end = "!endEvent";
	public final String settings = "!settings";
	ArrayList<Event> eventList = new ArrayList<Event>();

	public Schedule(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed("Schedule Bot",
				"!addEvent: Adds an event. Start with the event name, time, and date ex. (Valorant Grind 9:30pm pdt (tmr/fri/6/21)) \n"
						+ "!removeEvent: Removes an event. type the number or name of the event");
	}

	@Override
	public void handle(MessageCreateEvent discord) {
		String messageContent = discord.getMessageContent();
		// thsi will add the strings to the eventList
		if (messageContent.startsWith(add)) {
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
						System.out.println("A : was found at " + (eventString.indexOf(i + ":") + 1));
						isThereTime = true;
					}
				}
				if (isThereTime == false) {
					discord.getChannel().sendMessage("No time included!");
					System.out.println("No time found");
				} else {
					System.out.println("A time was found");
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
					realTime = new Time(time.charAt(0) + "" + time.charAt(1) + "", time.charAt(3) + "" + time.charAt(4) + "");

					name = eventString.substring(0, indexOfTime - 3);
					time = eventString.substring(indexOfTime - 2, eventString.length()).trim();
				} else if (isANumber(eventString.charAt(indexOfTime - 1) + "")) {
					System.out.println("found at indexoftime -1");
					realTime = new Time(time.charAt(0) + "", time.charAt(2) + "" + time.charAt(3) + "");

					name = eventString.substring(0, indexOfTime - 2);
					time = eventString.substring(indexOfTime-1, eventString.length()).trim();
				} else {
					System.out.println("invalid time format");
					discord.getChannel().sendMessage("Invalid Format");
					realTime = null;
				}
				System.out.println("time after prossesing is " + realTime.getHour() + " " + realTime.getMin());

				System.out.println("name and realtime values set");
				// get the date from the time

				String[] dateTime = time.split(" ");
				date = dateTime[dateTime.length - 1];
				if (date.contains("tmr")) {
					System.out.println("the string \"tmr\" was found");

					Date d = new Date();
					realDate = (d.getDate() + 1) + "/" + d.getMonth() + "/" + d.getYear();
					System.out.println("realDate set");
				}

//				time = time.replace(" ", "");
				Event event = new Event(name, realTime, realDate);
				System.out.println("event constructed");
				eventList.add(event);
			} else {
				discord.getChannel().sendMessage("Invalid Format");
			}

			discord.getChannel()
					.sendMessage("The string recived was Name = |" + eventList.get(eventList.size() - 1).getName()
							+ "| Time = |" + eventList.get(eventList.size() - 1).getTime().getTimeAsString() + "|"
							+ " |Date = |" + "temp" + "|");

			System.out.println("Message sent to channel");
			// event.getChannel().sendMessage("Sending a message to the channel");
		}
		// this will remove an event from the list
		if (messageContent.startsWith(remove)) {

		}
	}

	void listEvents() {

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
