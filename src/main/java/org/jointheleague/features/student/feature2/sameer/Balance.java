package org.jointheleague.features.student.feature2.sameer;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.Color;

public class Balance extends Feature {
    public final String COMMAND = "!balance";

    public Balance(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "View your coin amount"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here

            String id = event.getMessageAuthor().getIdAsString();
            int coins = Data.getMoney(id);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Balance");
            try {
                embed.setAuthor(event.getMessageAuthor());
            } catch (NullPointerException error) {
            }
            embed.setDescription("You have :coin: " + coins + " coins");
            embed.setTimestampToNow();
            embed.setColor(new Color(174, 255,121));
            event.getChannel().sendMessage(embed);
        }
    }
}
