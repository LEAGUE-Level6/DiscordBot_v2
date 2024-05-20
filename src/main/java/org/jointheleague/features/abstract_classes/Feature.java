package org.jointheleague.features.abstract_classes;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public abstract class Feature implements MessageCreateListener {

    protected String channelName;

    public HelpEmbed helpEmbed;

    public Feature(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        event.getServerTextChannel().ifPresent(e -> {
            if (e.getName().equals(channelName)) {
                try {
                    handle(new ReceivedMessage(event));
                } catch (APIException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    public abstract void handle(ReceivedMessage event) throws APIException;

}