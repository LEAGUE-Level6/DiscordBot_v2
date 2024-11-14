package org.jointheleague.features.student.first_feature;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class FeatureOne extends FeatureTemplate {
    public final String COMMAND = "!joke";

    public FeatureOne(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command prints cheesy jokes."
        );
    }
//IGNORE
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Random ran = new Random();
            int ranInt = ran.nextInt(5);

            switch(ranInt){
                case 0:event.sendResponse("What’s the best thing about Switzerland? \n \n I don’t know, but the flag is a big plus.");
                break;
                case 1:event.sendResponse("I invented a new word! \n \n Plagiarism!");
                break;
                case 2:event.sendResponse("Hear about the new restaurant called Karma? \n \n There’s no menu: You get what you deserve.");
                    break;
                case 3:event.sendResponse("Did you hear about the claustrophobic astronaut? \n \n He just needed a little space.");
                    break;
                case 4:event.sendResponse("Why don’t scientists trust atoms? \n \n Because they make up everything.");
                    break;
                default:event.sendResponse("What’s the different between a cat and a comma? \n \n A cat has claws at the end of paws; A comma is a pause at the end of a clause. ");
                    break;
            }

        }
    }
}
