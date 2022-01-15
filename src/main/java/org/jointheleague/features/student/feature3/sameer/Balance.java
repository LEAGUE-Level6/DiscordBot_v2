package org.jointheleague.features.student.feature3.sameer;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.bson.Document;

import java.awt.*;
import java.util.List;

public class Balance extends Feature {

    public final String COMMAND = "!balance";
    Client client;

    public Balance(String channelName, Client client) {
        super(channelName);
        this.client = client;
        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "View a user's balance in Minco Penguin"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            List<User> users = event.getMessage().getMentionedUsers();
            User user;
            if (users.size() > 0) {
                user = users.get(0);
            } else {
                user = event.getMessage().getUserAuthor().get();
            }
            Document data = client.findOne(user.getIdAsString());
            int mincoDollars = (int) data.get("mincoDollars");
            int bank = (int) data.get("bank");
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(user.getName() + "'s Balance")
                    .setDescription(":coin: Minco Dollars: " + mincoDollars + "\n:dollar: Bank: " + bank + "\n:moneybag: Total: " + (mincoDollars + bank))
                    .setColor(new Color(173,251,125));
            event.getChannel().sendMessage(embed);
        }
    }

}
