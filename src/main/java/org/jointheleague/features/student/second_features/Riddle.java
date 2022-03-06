package org.jointheleague.features.student.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Riddle extends Feature {

    public final String COMMAND = "!riddle";
    private final Random random = new Random();
    private final String answer = "An egg";

    public Riddle(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Riddle game. You will be given a riddle, your goal is to get the answer.");
    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("What has to be broken before you can use it?");
        }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")) {

            //parse the guess from the message
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");


        }
            event.getChannel().sendMessage("Correct!  The answer was " + answer);
        }
    }
}
