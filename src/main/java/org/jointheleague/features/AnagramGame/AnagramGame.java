package org.jointheleague.features.AnagramGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class AnagramGame extends Feature {

    public final String COMMAND = "!gram";
    String answer = "";
    boolean gameStarted = false;
    MessageAuthor currentUser = null;
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
        if(!event.getMessageAuthor().isYourself()) {
        if(!gameStarted) {
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
        	if(messageContent.equalsIgnoreCase(COMMAND+" easy")) {
        		gameSetup(event,0);
        	}
        	else if(messageContent.equalsIgnoreCase(COMMAND+" meduim")) {
        		gameSetup(event,1);
        	}
        	else if(messageContent.equalsIgnoreCase(COMMAND+" hard")) {
        		gameSetup(event,2);
        	} 
        	else event.getChannel().sendMessage("To start game do "+COMMAND+" (easy,meduim,hard)");
        }
        } else if(event.getMessage().getAuthor().equals(currentUser)) {
        	if(messageContent.equalsIgnoreCase(answer)) {
        		event.getChannel().sendMessage("Congrats! You got it correct!");
        		gameStarted = false;
        	} else{
        		event.getChannel().sendMessage("Sorry, the correct answer was "+answer);
        		gameStarted = false;
        	}
        } else {
        	event.getChannel().sendMessage("Currently playing with "+currentUser.getName()+", please wait till after they answer");
        }
        }
    }

	private void gameSetup(MessageCreateEvent event,int dificulty) {
		gameStarted = true;
		currentUser = event.getMessageAuthor();
		try {
			BufferedReader read = null;
			int line = 0;
			if(dificulty ==0) {
			read = new BufferedReader(new FileReader("easy.txt"));
			} else if(dificulty==1) {
			read = new BufferedReader(new FileReader("meduim.txt"));
			}else {
				read = new BufferedReader(new FileReader("hard.txt"));
			}
			line = new Random().nextInt(499)+1;
			for(int i = 0; i < line;i++) {
				answer = read.readLine();
			}
			read.close();
			String question = "Your word is: ";
			System.out.println(answer);
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
			
			event.getChannel().sendMessage(question+"\n The next thing you type will be taken as your guess");
		} catch (Exception e) {
			e.printStackTrace();
		event.getChannel().sendMessage("Failed to get random word");
		}
	// e ee
	}

}

