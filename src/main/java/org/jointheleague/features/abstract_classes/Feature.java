package org.jointheleague.features.abstract_classes;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.io.IOException;

public abstract class Feature extends ListenerAdapter {

    protected String channelName;

    public HelpEmbed helpEmbed;

    public Feature(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getName().equals(channelName)) {
            try {
                handle(new ReceivedMessage(event));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    public abstract void handle(ReceivedMessage event) throws IOException;

}
