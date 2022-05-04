package org.jointheleague;

import java.time.Instant;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class GetUTC extends Feature {

	public final String COMMAND = "!time";

	public GetUTC(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Displays the current time");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		if (messageContent.startsWith(COMMAND)) {
			String date = Instant.now().toString();
			event.getChannel().sendMessage("The time in UTC is "+date);
		}
	}

}
