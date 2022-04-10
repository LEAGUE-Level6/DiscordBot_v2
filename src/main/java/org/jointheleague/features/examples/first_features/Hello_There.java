package org.jointheleague.features.examples.first_features;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.awt.*;

public class Hello_There extends Feature {

    public final String COMMAND = "!Hello There";

    public Hello_There(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Go watch Revenge of the Sith if you haven't already. Why are you even on this " +
                        "server if you haven't watched ROTS yet?"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("General Kenobi!");
            //EmbedBuilder embed = new EmbedBuilder().setTitle("GENERAL KENOBI!").setAuthor("General Grievous")
                  //  .addField("You are a bold one", "Your lightsaber will make a fine addition to my collection")
                  //  .setColor(Color.BLUE).setImage("https://lumiere-a.akamaihd.net/v1/images/General-Grievous_c9df9cb5.jpeg?region=0%2C0%2C1200%2C675&width=960")
                  //  .setFooter("So uncivilized");
        }
    }

}
