package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class treasureHunt extends Feature {

    public final String COMMAND = "!treasureHunt";
    private final Random random = new Random();
    int location;

    public treasureHunt(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Go on a search for treasure by inputting a value to find the location."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {

            location = random.nextInt(50) + 1;
            event.getChannel().sendMessage("Enter a number below 50! Guess by saying e.g. !treasureHunt 43");
        } else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")) {

            if (location == 0) {
                event.getChannel().sendMessage("You must begin the game, you cannot explore an island you have not yet reached!");
                return;
            }


            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
 int guess = Integer.parseInt(guessMessage);


            if (guess > location) {
                event.getChannel().sendMessage("Too high! Guess lower :)");
            } else if (guess < location) {
                event.getChannel().sendMessage("Too low! Guess higher :)");
            } else {

                event.getChannel().sendMessage("Nice Job! You found the treasure at " + location);



            }

        }
    }

}

