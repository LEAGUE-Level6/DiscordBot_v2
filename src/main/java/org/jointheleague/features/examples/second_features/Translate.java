package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Translate extends Feature {

    public final String COMMAND = "!translate";

    public Translate(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Translate text! Usage:"
        		+ "\n!translate languageCodeFrom langaugeCodeTo text to translate,"
        		+ "\nexample: !translate en fr Hi, how are you?,"
        		+ "\nor to just pull up google translate: !translate, !translate en fr");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	
        	String[] words = messageContent.split(" ");
        	String outMessage;
        	String section1 = "https://translate.google.com/?";
        	
        	if (words.length == 1) {
        		outMessage = section1;
        	}
        	else if (words.length < 3) {
        		outMessage = "Incorrect usage of !translate";
        	}
        	else {
            	String langFrom = words[1].substring(0, 2);
            	String langTo = words[2].substring(0, 2);
            	String section2 = "sl=" + langFrom + "&tl=" + langTo + "&";
            	
            	String text = "text=";
            	for (int i = 3; i < words.length; i++) {
            		text = text + words[i] + "%20";
            	}
            	            	
            	String ending = "&op=translate";
            	outMessage = section1 + section2 + text + ending;
        	}
        	
            event.getChannel().sendMessage(outMessage);
        }
    }

}