package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class SynonymFinder extends Feature {

	String COMMAND = "!synonym";
	public SynonymFinder(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "gets a synonym for an entered word");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String messageContent = event.getMessageContent();
		// TODO Auto-generated method stub
		
	}

}
