package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CookieClicker extends Feature {

    public final String COMMAND = "!cookieClicker";

    public CookieClicker(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Bake cookies, buy upgrades, and climb the leaderboard to become the ultimate baker!"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals("!fishingFrenzy help")) {
            //respond to message here
            event.sendResponse("Commands:\n`!cookieCliker click` to bake a cookie.\n`!cookieClicker cookies` to see how much cookies you have.\n`!cookieClicker upgrades` to buy upgrades.");
        }
        
    }

}
