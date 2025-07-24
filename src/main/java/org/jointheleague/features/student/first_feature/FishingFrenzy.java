package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class FishingFrenzy extends Feature {

    public final String COMMAND = "!fishingFrenzy";

    public FishingFrenzy(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Cast your line and catch all kinds of fish — from common to legendary! Earn coins, build your collection, and compete on the leaderboard."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            //respond to message here
            event.sendResponse("Commands:\n`!fishingFrenzy fish` to cast your line\n`!fishingFrenzy inventory` to see your inventory. \n`!fishingFrenzy leaderboard` to check out where your standings are. ");
        }
        else if (messageContent.endsWith("fish")) {
        	
        	event.sendResponse("You wanted to fish, but was rejected. xD");
        }
    }

}
