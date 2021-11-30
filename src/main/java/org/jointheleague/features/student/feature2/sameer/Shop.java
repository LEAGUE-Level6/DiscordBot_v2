package org.jointheleague.features.student.feature2.sameer;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.awt.*;

public class Shop extends Feature {
    public final String COMMAND = "!shop";

    public Shop(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "View the shop (things you can buy)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            EmbedBuilder embed = new EmbedBuilder();
            try {
                embed.setAuthor(event.getMessageAuthor());
            } catch (NullPointerException error) {

            }
            embed.setColor(new Color(252, 179, 68));
            for (int i = 0; i < Data.formattedItems.length; i++) {
                embed.setTitle("Shop");
                embed.addField(Data.formattedItems[i], "Price: :coin: " + Data.prices[i]);
            }
            event.getChannel().sendMessage(embed);
        }
    }
}
