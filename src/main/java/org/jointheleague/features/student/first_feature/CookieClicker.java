package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CookieClicker extends Feature {

    public final String COMMAND = "!cookieClicker";
    int cookies = 0;
    int click = 1;
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
        if (messageContent.equals("!cookieClicker help")) {
            //respond to message here
            event.sendResponse("Commands:\n`!cookieCliker click` to bake a cookie.\n`!cookieClicker cookies` to see how much cookies you have.\n`!cookieClicker upgrades` to buy upgrades.");
        }
        if (messageContent.equals("!cookieClicker click")) {
        	event.sendResponse("You gained " + click + " cookie");
        	cookies += click;
        }
        if (messageContent.equals("!cookieClicker cookies")) {
        	event.sendResponse("You have " + cookies + " cookies in total.");
        }
        if (messageContent.equals("!cookieClicker upgrades")) {
        	event.sendResponse("Upgrades: Increases cookies per click. To buy, just type `!cookieClicker [upgrade name]`\nCursor (+1): 10 cookies\nGramma (+5): 50 cookies\nFarm (+10): 100 cookies\nMine (+50): 300 cookies");
        }
        if (messageContent.startsWith(COMMAND) && messageContent.endsWith("cursor")) {
        	click += 1;
        	cookies -= 10;
        }
        if (messageContent.startsWith(COMMAND) && messageContent.endsWith("gramma")) {
        	click += 5;
        	cookies -= 50;
        }
        if (messageContent.startsWith(COMMAND) && messageContent.endsWith("farm")) {
        	click += 10;
        	cookies -= 100;
        }
        if (messageContent.startsWith(COMMAND) && messageContent.endsWith("mine")) {
        	click += 50;
        	cookies -= 300;
        }
    }

}
