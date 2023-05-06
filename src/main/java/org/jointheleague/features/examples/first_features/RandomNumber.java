package org.jointheleague.features.examples.first_features;

import java.util.Random;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class RandomNumber extends Feature {

    public final String COMMAND = "!random";

    public RandomNumber(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number between 0 and 1000)");
    }

    @Override
    public void handle(MessageCreateEvent event) {

        System.out.println("HANDLE!");
        String messageContent = event.getMessageContent();
        System.out.println("MESSAGE: "+messageContent);
        if (messageContent.startsWith(COMMAND)) {
            Random r = new Random();
            event.getChannel().sendMessage("Your random number is " + r.nextInt(1001));
        }
    }

}
