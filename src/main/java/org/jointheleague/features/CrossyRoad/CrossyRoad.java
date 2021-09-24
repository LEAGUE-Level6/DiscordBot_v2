 package org.jointheleague.features.CrossyRoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CrossyRoad extends Feature{
	public final String COMMAND = "!road";
    HashMap<MessageAuthor, Holder> games = new  HashMap<MessageAuthor,Holder>();
    MessageAuthor currentSetup = null;
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
    	if(event.getMessageAuthor().isYourself()&&messageContent.equals("setup")) {
    	games.put(currentSetup, setUpGame(event));
    	games.get(currentSetup).t.start();
    	currentSetup=null;
    	System.out.println("ee");

    	}
        if (messageContent.startsWith(COMMAND)) {
            if(messageContent.contentEquals(COMMAND+" start")) {
            	if(!games.containsKey(auth)) {
            		currentSetup = auth;
            		event.getChannel().sendMessage("setup");
            	            	} else {
            		event.getChannel().sendMessage("You are already in a game, either do !road end or finish the game before starting a new game");
            	}
            } else if ((COMMAND+" end").equals(messageContent)) {
            	games.get(auth).r.kill();
            }            
        }
    }

	private Holder setUpGame(MessageCreateEvent e) {
		Row[] rows = new Row[7];
		for(int i = 0; i < rows.length-1;i++) {
			int x = new Random().nextInt(10);
			if(x < 5) {
			rows[i] = new Row(0,false);
			} else if (x < 7) {
			rows[i] = new Row(1,false);
			} else if (x < 9) {
			rows[i] = new Row(2,false);
			} else {
			rows[i] = new Row(3,false);
			}
		}
		rows[6] = new Row(0,true);
		runningGame r = new runningGame(rows,e);
		return new Holder(new Thread(r),r);
	}

}
