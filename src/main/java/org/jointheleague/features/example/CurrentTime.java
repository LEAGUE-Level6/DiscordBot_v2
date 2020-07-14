package org.jointheleague.features.example;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discordbot.CustomMessageCreateListener;
import org.jointheleague.pojo.help_embed.HelpEmbed;

public class CurrentTime extends CustomMessageCreateListener {

	public final String COMMAND = "!time";

	public CurrentTime(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Displays the current time");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			SimpleDateFormat formatter = new SimpleDateFormat("'The time is 'HH:mm:ss z' on 'MM-dd-YYYY'.'");
			Date date = new Date(System.currentTimeMillis());
			
			event.getChannel().sendMessage(formatter.format(date));
		}
	}

}
