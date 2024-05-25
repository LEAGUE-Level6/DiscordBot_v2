package org.jointheleague.features.whale;

import java.util.ArrayList;

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
			String eventString = messageContent.substring(add.length()).trim();
			if (eventString.contains(" ")) {
				String[] values = eventString.split(" ");
				String time = "";
				String name = "";
				String date = "";

				boolean isThereTime = false;
				ArrayList<Integer> indexOfTimes = new ArrayList<Integer>();
				for (int i = 0; i < 11; i++) {
					if (eventString.contains(i + ":")) {
						indexOfTimes.add(eventString.indexOf(i + ":"));
						isThereTime = true;
					}
				}
				if (isThereTime == false) {
					discord.getChannel().sendMessage("No time included!");
				}
				int indexOfTime = Integer.MAX_VALUE;
				for (int i = 0; i < indexOfTimes.size(); i++) {
					if (indexOfTimes.get(i) < indexOfTime) {
						indexOfTime = indexOfTimes.get(i);
					}
				}

				

				name = eventString.substring(0, indexOfTime - 2);
				time = eventString.substring(indexOfTime - 1, eventString.length()).trim();
				time = time.replace(" ", "");
				Event event = new Event(name, time, date);
				eventList.add(event);
			} else {
				discord.getChannel().sendMessage("Invalid Format");
			}

			discord.getChannel()
					.sendMessage("The string recived was Name = |" + eventList.get(eventList.size() - 1).getName()
							+ "| Time = |" + eventList.get(eventList.size() - 1).getTime() + "|");

			// event.getChannel().sendMessage("Sending a message to the channel");
		}
		// this will remove an event from the list
		if (messageContent.startsWith(remove)) {

		}
	}

	void listEvents() {

	}

	void format() {
		for (int i = 0; i < eventList.size(); i++) {

		}
	}

}
