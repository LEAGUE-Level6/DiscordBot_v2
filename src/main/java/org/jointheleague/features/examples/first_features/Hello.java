package org.jointheleague.features.examples.first_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Hello extends Feature {

    public final String COMMAND = "!hello";

    public Hello(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "The bot says hello (usage: !hello [name])");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            Random r = new Random();
            String[] hellos = { "Hi", "Hello", "Bonjour", "Salut", "Hey"};
            int hellosIndex = r.nextInt(hellos.length);
            String person;

            String name = messageContent.replaceAll(" ", "").replace(COMMAND, "");

            if (name.equals("")) {
            	person = event.getMessageAuthor().getDisplayName();
            	if (person == null) {
            		person = "";
            	}
            }
            else {
            	person = name;
            }


            String outMessage = hellos[hellosIndex] + " " + person;

            event.getChannel().sendMessage(outMessage);
        }
    }

}