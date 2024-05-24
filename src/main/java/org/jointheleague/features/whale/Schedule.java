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
		helpEmbed = new HelpEmbed(add,
				"Adds an event. Start with the event name and time ex. (Valorant Grind 9:30pdt)");
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
				if (values[values.length - 1].contains("0") || values[values.length - 1].contains("1")
						|| values[values.length - 1].contains("2") || values[values.length - 1].contains("3")
						|| values[values.length - 1].contains("4") || values[values.length - 1].contains("5")
						|| values[values.length - 1].contains("6") || values[values.length - 1].contains("7")
						|| values[values.length - 1].contains("8") || values[values.length - 1].contains("9")
						|| values[values.length - 1].contains("10")) {
					
					time = values[values.length - 1];
					for(int i = 0; i < values.length-1; i++) {
						name += values[i] + " ";
					}
				}
				else {
					time = values[values.length-2] + values[values.length - 1];
					for(int i = 0; i < values.length-2; i++) {
						name += values[i] + " ";
					}
				}
				 Event event = new Event(name, time);
				eventList.add(event);
			} else {
				discord.getChannel().sendMessage("Invalid Format");
			}

			discord.getChannel().sendMessage("The string recived was Name = |" + eventList.get(eventList.size()-1).getName() + "| Time = |" + eventList.get(eventList.size()-1).getTime() +"|");
			// event.getChannel().sendMessage("Sending a message to the channel");
		}
	}

	void listEvents() {

	}

	void format() {
		for (int i = 0; i < eventList.size(); i++) {

		}
	}

}
