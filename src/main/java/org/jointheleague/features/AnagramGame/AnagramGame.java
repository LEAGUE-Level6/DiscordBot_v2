package org.jointheleague.features.AnagramGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class AnagramGame extends Feature {

    public final String COMMAND = "!gram";
    String answer = "";
    public AnagramGame(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This is a game where you are provided with a scrambled word and you need to figure out what it is"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
        	if(messageContent.equalsIgnoreCase(COMMAND)) {
        		gameSetup(event);
        	}
            
        }
    }

	private void gameSetup(MessageCreateEvent event) {
		try {
			BufferedReader read = new BufferedReader(new FileReader("/Users/league/git/DiscordBot_v2/src/main/java/dictionary.txt"));
			int line = new Random().nextInt(255);
			for(int i = 0; i < line;i++) {
				answer = read.readLine();
			}
			read.close();
			String question = "Your word is: ";
			char[] e = answer.toCharArray();
			for(int i = 0; i < e.length/2;i++) {
				int newRan = new Random().nextInt(e.length/2)+e.length/2;
				char holder = e[newRan];
				e[newRan] = e[i];
				e[i]=holder;
			}
			for(int i = 0; i < e.length;i++) {
				question += ""+e[i];
			}
			event.getChannel().sendMessage(question);
		} catch (Exception e) {
			e.printStackTrace();
		event.getChannel().sendMessage("Failed to get random word");
		}
	// e
	}

}

