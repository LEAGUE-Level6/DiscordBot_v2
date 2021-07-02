package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class ReverseText extends Feature {

	public final String COMMAND = "!reverse";

	public ReverseText(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND, "Reverses the string that is typed after the command.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		messageContent.trim();

		if (messageContent.startsWith(COMMAND)) {

			if (messageContent.length() > COMMAND.length()) {
				messageContent = messageContent.substring(COMMAND.length(), messageContent.length());
				messageContent = messageContent.trim();

				String finalMessage = "";
				for (int i = messageContent.length() - 1; i >= 0; i--) {
					finalMessage += messageContent.charAt(i);
				}

				// respond to message here
				event.getChannel().sendMessage(finalMessage);
			} else {
				event.getChannel().sendMessage("Please input some text to be reversed after the command.");
			}
		}
	}
}
