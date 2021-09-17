 package org.jointheleague.features.CrossyRoad;

import java.util.ArrayList;
import java.util.HashMap;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CrossyRoad extends Feature{
	public final String COMMAND = "!road";
    HashMap<MessageAuthor, Thread> games = new  HashMap<MessageAuthor,Thread>();
    
    public CrossyRoad(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        MessageAuthor auth = event.getMessageAuthor();
        if (messageContent.startsWith(COMMAND)) {
            if(messageContent.contentEquals(COMMAND+" start")) {
            	if(!games.containsKey(auth)) {
            	games.put(auth, setUpGame());
            	
            	} else {
            		event.getChannel().sendMessage("You are already in a game, either do !road end or finish the game before starting a new game");
            	}
            }
            event.getChannel().sendMessage("Sending a message to the channel");
            
        }
    }

	private Thread setUpGame() {
		String[][] gameRay = new String[5][7];
		for(int i = 0; i < gameRay.length-1;i++) {
			//STUFF HERE ENEDS TO CHANGE
		}
		return null;
	}

}
