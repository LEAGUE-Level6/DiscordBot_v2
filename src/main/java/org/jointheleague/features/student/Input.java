package org.jointheleague.features.student;


import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Arrays;
import java.util.Locale;

public class Input extends Feature {

    public final String COMMAND = "I'm . . .";

    public Input(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Greats you by your name"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (!event.getAuthor().equals("1424893152765935738")){
            if (messageContent.toLowerCase().contains("im")) {
                event.sendResponse("Hey " + caps(messageContent.substring(messageContent.toLowerCase().indexOf("im") + 3)) + ", the name's LeBron");
            } else if (messageContent.toLowerCase().contains("i'm")) {
                event.sendResponse("Hey " + caps(messageContent.substring(messageContent.toLowerCase().indexOf("i'm") + 4)) + ", the name's LeBron");
            }
    }
}
    public String caps(String in){
        String[] parts = in.split(" ");
        for (int i = 0; i<parts.length; i++){
            parts[i]=parts[i].substring(0, 1).toUpperCase(Locale.ROOT)+parts[i].substring(1);
        }
        String out = "";
        for (int i = 0; i<parts.length; i++){
            out+=" "+parts[i];
        }
        return out.substring(1);
    }

}
