package org.jointheleague.features.abstract_classes;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public abstract class Feature extends ListenerAdapter
{

    protected String channelName;

    public HelpEmbed helpEmbed;

    public Feature(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getName().equals(channelName)) {
            handle(new ReceivedMessage(event));
        }
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
    }
    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    public abstract void handle(ReceivedMessage event);

}