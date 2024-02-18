package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class FeatureOne extends FeatureTemplate {
	public final String COMMAND = "!introduce";

    public FeatureOne(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "I will tell you a little bit about who I am and what I can do!"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("I am NicholasBot, created by the one and only, Nicholas Falstad. I have all sorts of features such as... (fill in later)");
        }
    }
    }

