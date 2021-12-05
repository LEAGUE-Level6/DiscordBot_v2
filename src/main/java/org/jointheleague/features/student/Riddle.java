package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import java.util.Random;

public class Riddle extends Feature{
    public final String RIDDLE = "!riddle";
    public final String GUESS = "!guess";
    boolean gameStarted = false;

    public Riddle(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                RIDDLE,
                "type !riddle to receive a riddle, then type your response after !guess"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        String[] failMessages = {"Try again.", "Obtain a braincell.", "You got this."};
        Random random = new Random();

        if (messageContent.startsWith(RIDDLE)) {
            event.getChannel().sendMessage("If a duck was given $9, a spider was given" +
                    " $36, and a bee was given $27, how much will be given to a cat?");
            gameStarted = true;
        }
        if (gameStarted == true && messageContent.startsWith(GUESS)) {
            if (!messageContent.contains("18")) {
                event.getChannel().sendMessage(failMessages[random.nextInt(2)]);
            } else {
                event.getChannel().sendMessage("Good job.");
            }
        } else if (gameStarted != true && messageContent.startsWith(GUESS)) {
                event.getChannel().sendMessage("Game hasn't started.");
        }
    }

}
