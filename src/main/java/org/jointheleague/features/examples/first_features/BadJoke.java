package org.jointheleague.features.examples.first_features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class BadJoke extends Feature {

    public final String COMMAND = "!badJoke";

    public BadJoke (String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Says bad joke; reading the description is unnecessary." +
               "Why are you still reading? Stop, and see my bad joke already. "  );
    }
    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String messageContent = event.getMessageContent();
        System.out.println(messageContent);
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("Knock knock... who's there? Dr... Dr Who? You just said it.");
        }
    }
}
