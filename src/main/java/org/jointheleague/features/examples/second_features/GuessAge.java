package org.jointheleague.features.examples.second_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class GuessAge extends Feature {
    public final String COMMAND = "!guessAge";
    int ageGuess = 16;

    public GuessAge(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Give me your age, and I will determine if you're old enough to drive");
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String messageContent = event.getMessageContent();
        int guess = Integer.parseInt(messageContent);
        if (guess > ageGuess) {
            event.getChannel().sendMessage("You are old enough to drive!");
        } else {
            int years = ageGuess - guess;
            event.getChannel().sendMessage("Unfortunately you need to be" + guess + " to drive");

        }
    }
}
