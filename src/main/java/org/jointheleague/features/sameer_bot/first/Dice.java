package org.jointheleague.features.sameer_bot.first;

import java.util.Random;
import java.awt.Color;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Dice extends Feature {

    public final String COMMAND = "!dice";

    public Dice(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Sends a random number between 1 and 6"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            int num = new Random().nextInt(6)+1;
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(247, 249, 249)); // white
            embed.setTitle(":game_die: " + num);
            event.getChannel().sendMessage(embed);
        }
    }
}
