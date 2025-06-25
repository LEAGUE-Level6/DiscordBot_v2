package org.jointheleague.features.student.first_feature;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class FeatureOne extends FeatureTemplate {
    public final String COMMAND = "fob??";
    public final String password = "7219";

    public FeatureOne(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Fob Fobbin"
        );
    }
//IGNORE
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND) && messageContent.contains(password)) {
        	event.sendResponse("how did you guess the password?!");
        }
        else if(messageContent.startsWith(COMMAND)) {
        	event.sendResponse("How can I help? Do not expect responses in a timely manner.");
        }
        else if(messageContent.strip().startsWith("DIE!")) {
        	event.sendResponse("You killed Fob! What's wrong with you??");
        	System.exit(0);
        }
        else if(messageContent.strip().toLowerCase().startsWith("please help me with this math problem")) {
        	event.sendResponse("It's simple the answer is " + new Random().nextInt(100));
        }
    }
}
