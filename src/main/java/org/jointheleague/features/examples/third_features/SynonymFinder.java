package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import net.aksingh.owmjapis.api.APIException;

public class SynonymFinder extends Feature {

	public final String COMMAND = "!synonym";
	
	
	private WebClient webClient;
	private static String baseUrl = "";
	
	public SynonymFinder(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "Gets a synonym (according to Thesaurus.com, at least) for an entered word.");
		
		this.webClient = WebClient
				.builder()
				.baseUrl(baseUrl)
				.build();
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String messageContent = event.getMessageContent();
		// TODO Auto-generated method stub
		if(messageContent.contains(COMMAND)) {
			messageContent = messageContent.replaceAll(" ", "").replace(COMMAND, "");
			if(messageContent.equals("")) {
				event.getChannel().sendMessage("Please format your command: '!synonym [word]");
			}
			
			else {
				event.getChannel().sendMessage(getSynonym(messageContent));
			}
			
		}
	}
	public String getSynonym(String inquiry) {
		
		return "We could not find any synonyms for that word (because I'm not done with this program yet).";
	}
	

}
