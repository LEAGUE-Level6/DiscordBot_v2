package org.jointheleague.api_wrapper;

import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class ReceivedMessage {

    private MessageCreateEvent event;

    public ReceivedMessage(MessageCreateEvent event) {
        this.event = event;
    }

    public String getMessageContent() {
        return this.event.getMessageContent();
    }

    public void sendResponse(String message) {
        this.event.getChannel().sendMessage(message);
    }

    public CompletableFuture<Message> sendResponse(EmbedBuilder embed) {
        return this.event.getChannel().sendMessage(embed);
    }

}