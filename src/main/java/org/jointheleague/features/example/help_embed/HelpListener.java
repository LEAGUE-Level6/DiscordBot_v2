package org.jointheleague.features.example.help_embed;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discordbot.CustomMessageCreateListener;
import org.jointheleague.pojo.help_embed.HelpEmbed;

public class HelpListener extends CustomMessageCreateListener {

	public final String COMMAND = "!help";
	private List<HelpEmbed> helpEmbeds = new ArrayList<>();

	public HelpListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			for(HelpEmbed helpEmbed : helpEmbeds) {
				event.getChannel().sendMessage(helpEmbed.getEmbed());
			}
		}
	}
	
	public void addHelpEmbed(HelpEmbed helpEmbed) {
		helpEmbeds.add(helpEmbed);
	}
	
	
}
