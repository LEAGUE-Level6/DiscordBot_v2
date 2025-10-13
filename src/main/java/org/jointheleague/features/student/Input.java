package org.jointheleague.features.student;


import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Locale;

public class Input extends Feature {

    public final String COMMAND = "im";

    public Input(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.toLowerCase().contains(COMMAND) || messageContent.toLowerCase().contains("i am") || messageContent.toLowerCase().contains("i'm")) {
            //respond to message here
            event.sendResponse("Hey "+messageContent.substring(messageContent.indexOf("im")+2) + ", the name's LeBron");
        }
    }

}
