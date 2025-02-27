package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import java.util.Random;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!chill";

    public FeatureTwo(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Just chill out the chat, be a chill guy."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            int rand = new Random().nextInt(3);
            switch (rand){
                case 0:
                    event.sendResponse("You all need to become chill guys, just like me.");
                    break;

                case 1:
                    event.sendResponse("Chill out man, we are all chill guys here.");
                    break;

                case 2:
                    event.sendResponse("This chat needs to chill fr fr, calm down.");
                    break;
            }


        }
    }

}
