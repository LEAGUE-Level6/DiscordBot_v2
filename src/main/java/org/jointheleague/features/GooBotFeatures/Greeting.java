package org.jointheleague.features.GooBotFeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class Greeting extends Feature {

    public final String COMMAND = "!greeting";

    public Greeting(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(COMMAND, "Says hello in a kind, thoughtful way");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        Random ran = new Random();
        ArrayList <String> responses = new ArrayList<String>();
        responses.add("Good day to you! I’m  GooBot.");
        responses.add("Pleased to meet you. My name is GooBot");
        responses.add("How are you? I hope you are doing well. I'm GoodBot by the way");
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage(responses.get(ran.nextInt(3)));
        }
    }

}
