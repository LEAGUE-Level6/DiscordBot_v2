package org.jointheleague.features.student;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Locale;

public class Hooray extends Feature {

    public final String COMMAND = "/test";

    public Hooray(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Gets you in the christmas spirit"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.toLowerCase().startsWith(COMMAND)) {
            //respond to message here
            event.sendResponse("M");
        }
    }

}
