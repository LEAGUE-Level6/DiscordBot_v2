package org.jointheleague.features.student;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class TryAgain extends FeatureTemplate {
    public final String COMMAND = "/run";

    public TryAgain(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command doesn't really work"
        );
    }
    //IGNORE
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Random ran = new Random();
            int ranInt = ran.nextInt(6);

            switch(ranInt){
                case 0:event.sendResponse("Command didn't work");
                    break;
                case 1:event.sendResponse("Please try again later");
                    break;
                case 2:event.sendResponse("You forgot to start the bot");
                    break;
                case 3:event.sendResponse("Uninstalling...");
                    break;
                case 4:event.sendResponse("You typed it wrong");
                    break;
                case 5:event.sendResponse("Success!");
                    break;
                default:event.sendResponse("Exception in thread \"main\" java.lang.UnsupportedClassVersionError");
                    break;
            }

        }
    }
}
