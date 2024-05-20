package org.jointheleague.api_wrapper;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReceivedMessage {

    private MessageReceivedEvent event;

    public ReceivedMessage(MessageReceivedEvent event) {
        this.event = event;
    }

    public String getMessageContent() {
        return this.event.getMessage().getContentStripped();
    }

    public void sendResponse(String message) {
        this.event.getChannel().sendMessage(message).submit().join();
    }

    public Message sendResponse(MessageEmbed embed) {
        return this.event.getChannel().sendMessageEmbeds(embed).submit().join();
    }

}