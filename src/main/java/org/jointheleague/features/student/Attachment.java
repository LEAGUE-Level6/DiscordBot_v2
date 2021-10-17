package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Attachment extends Feature{
    public final String ATTACH4 = "!attach4";
    boolean gameStarted = false;
    boolean sent = false;

    public Attachment(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                ATTACH4,
                "type !attach4 to start the game, then pick through columns 1-6 on each turn " +
                        "to place pieces"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        char[][] field = new char[][] {{'O', 'O', 'O', 'O', 'O'},{}'O', 'O', 'O', 'O', 'O'
                ,{'O', 'O', 'O', 'O', 'O'},{'O', 'O', 'O', 'O', 'O'},{'O', 'O', 'O', 'O', 'O'}};

        if (messageContent.startsWith(ATTACH4)) {
            event.getChannel().sendMessage("If a duck was given $9, a spider was given" +
                    " $36, and a bee was given $27, how much will be given to a cat?");
            gameStarted = true;
        }

        if (gameStarted == true) {
            if (!messageContent.contains("1")) {

            } else if (!messageContent.contains("2")) {

            } else if (!messageContent.contains("3")) {

            } else if (!messageContent.contains("4")) {

            } else if (!messageContent.contains("5")) {

            } else if (!messageContent.contains("6")) {

            }
        }

        if(!gameStarted && !sent) {
            event.getChannel().sendMessage("Game hasn't started.");
            sent = true;
        }

    }
}
