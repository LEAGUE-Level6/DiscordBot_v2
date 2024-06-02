package org.jointheleague.features.help_embed;

import java.util.ArrayList;
import java.util.List;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;

public class HelpListener extends Feature {

	public final String COMMAND = "!help";
	private List<HelpEmbed> helpEmbeds = new ArrayList<>();

	public HelpListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(ReceivedMessage event) {
		if(event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			for(HelpEmbed helpEmbed : helpEmbeds) {
				event.sendResponse(helpEmbed.getEmbed());
			}
		}
	}
	
	public void addHelpEmbed(HelpEmbed helpEmbed) {
		helpEmbeds.add(helpEmbed);
	}
	
	
}
