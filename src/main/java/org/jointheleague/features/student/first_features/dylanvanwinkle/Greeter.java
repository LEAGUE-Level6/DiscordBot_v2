package org.jointheleague.features.student.first_features.dylanvanwinkle;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Greeter extends Feature {

    public final String COMMAND = "!greeter";

    public Greeter(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Greets you");
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("Hello " + event.getMessageAuthor().getName() + ".");
        }
    }
}
