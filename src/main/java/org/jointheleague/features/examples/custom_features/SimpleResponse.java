package org.jointheleague.features.examples.custom_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import javax.security.auth.login.LoginException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleResponse extends Feature {
    public final String COMMAND = "!response";
    public SimpleResponse(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Displays a response");
    }
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String response = "New ideas";
            SimpleDateFormat formatter = new SimpleDateFormat("'Hello'");
            event.getChannel().sendMessage(formatter.format(response));
        }
    }
}
