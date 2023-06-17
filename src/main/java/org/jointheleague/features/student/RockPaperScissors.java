package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.first_features.RandomNumber;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;


public class RockPaperScissors extends Feature {

    public final String COMMAND = "!rps";

    public RockPaperScissors(String channelName) {
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
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here

            Random ran = new Random();
            int random = ran.nextInt(3);

            if(random == 0){
                event.getChannel().sendMessage("Rock");
            }else if(random == 1){
                event.getChannel().sendMessage("Paper");
            }else if(random == 2){
                event.getChannel().sendMessage("Scissors");
            }
        }
    }

}
