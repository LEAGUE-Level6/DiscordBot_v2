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
    ArrayList<String[]> eventList = new ArrayList<String[]>();

    public Schedule(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
        		add,
                "Adds an event. Start with the event name and time ex. (Valorant Grind 9:30pdt)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        //thsi will add the strings to the eventList
        if (messageContent.startsWith(add)) {
        	String eventString = messageContent.substring(add.length()).trim();
        	if (eventString.contains(" ")) {
        	String[] values = eventString.split(" ");
        	 eventList.add(values);
        	}
        	else {
        		event.getChannel().sendMessage("Invalid Format");
        	}
        	
        	
        	event.getChannel().sendMessage("The string recived was " + eventList.get(0)[0] + " " + eventList.get(0)[1]);
            //event.getChannel().sendMessage("Sending a message to the channel");
        }
    }

}
