package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;


public class RockPaperScissorsGame extends Feature {

    public final String COMMAND = "!grps";

    public RockPaperScissorsGame(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Play rock paper scissors with computer."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        if ((messageContent.startsWith(COMMAND) && messageContent.contains("Rock")) || (messageContent.startsWith(COMMAND) && messageContent.contains("Paper")) || (messageContent.startsWith(COMMAND) && messageContent.contains("Scissors"))) {
            //respond to message here

            Random ran = new Random();
            int random = ran.nextInt(3);

            if(random == 0){
                event.getChannel().sendMessage("Rock");
                if(messageContent.contains("Rock")){
                    event.getChannel().sendMessage("Its a draw");
                }
                if(messageContent.contains("Paper")){
                    event.getChannel().sendMessage("You win");
                }
                if(messageContent.contains("Scissors")){
                    event.getChannel().sendMessage("I win");
                }
            }else if(random == 1){
                event.getChannel().sendMessage("Paper");
                if(messageContent.contains("Rock")){
                    event.getChannel().sendMessage("I win");
                }
                if(messageContent.contains("Paper")){
                    event.getChannel().sendMessage("Its a draw");
                }
                if(messageContent.contains("Scissors")){
                    event.getChannel().sendMessage("I lose");
                }
            }else if(random == 2){
                event.getChannel().sendMessage("Scissors");
                if(messageContent.contains("Rock")){
                    event.getChannel().sendMessage("I lose");
                }
                if(messageContent.contains("Paper")){
                    event.getChannel().sendMessage("I win");
                }
                if(messageContent.contains("Scissors")){
                    event.getChannel().sendMessage("Its a draw");
                }
            }
        }


    }

}
