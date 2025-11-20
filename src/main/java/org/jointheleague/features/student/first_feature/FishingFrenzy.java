package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;

import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Random;
public class FishingFrenzy extends Feature {
	public int coins = 0;
    public final String COMMAND = "!fishingFrenzy";

    public FishingFrenzy(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Cast your line and catch all kinds of fish — from common to legendary! Earn coins, buy upgrades, and compete on the leaderboard."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals("!fishingFrenzy help")) {
            //respond to message here
            event.sendResponse("Commands:\n`!fishingFrenzy cast` to cast your line.\n`!fishingFrenzy balance` to see how much coins you have. ");
        }
        else if (messageContent.equals("!fishingFrenzy fish")) {
        	Random r = new Random();
            double randomNumber = r.nextDouble() * 100;
            fishdrops(event, randomNumber);
        }
        else if (messageContent.equals("!fishingFrenzy balance")) {
        	event.sendResponse("You have " + coins + " coins.");
        }
    }

	public void fishdrops(ReceivedMessage event, double randomNumber) {
		if (randomNumber < 1) {
		    event.sendResponse("You caught a Dragonfish! (Legendary: 1%)\nYou gained 750 coins!");
		    coins+=750;
		} else if (randomNumber < 5){
		    event.sendResponse("You caught a Golden Koi! (Mythic: 4%)\nYou gained 300 coins!");
		    coins+=300;
		}
		else if (randomNumber < 15) {
			event.sendResponse("You caught a Swordfish! (Epic: 10%)\nYou gained 150 coins!");
			coins+=150;
		}
		else if (randomNumber < 30) {
			event.sendResponse("You caught a Salmon! (Rare: 15%)\nYou gained 75 coins!");
			coins+=75;
		}
		else if (randomNumber < 55) {
			event.sendResponse("You caught a Mackeral! (Uncommon: 25%)\nYou gained 30 coins!");
			coins+=30;
		}
		else {
			event.sendResponse("You caught a Carp! (Common: 45%)\nYou gained 10 coins!");
			coins+=10;
		}
	}

}
