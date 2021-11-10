package org.jointheleague.features.sameerbot.third;

import org.bson.Document;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zoo extends Feature {
    public final String COMMAND = "!zoo";
    Client client;

    public Zoo(String channelName, Client client) {
        super(channelName);
        this.client = client;

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "View your zoo"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            List<User> users = event.getMessage().getMentionedUsers();
            User user;
            if (users.size() > 0) {
                user = users.get(0);
            } else {
                user = event.getMessage().getUserAuthor().get();
            }
            String toSend = "";
            Document profile = client.findOne(user.getIdAsString());
            ArrayList<Document> zoo = (ArrayList<Document>) profile.get("zoo");
            if (zoo.size() == 0) {
                event.getChannel().sendMessage(user.getName() + " doesn't have any animals in their zoo");
                return;
            }
            for (int i = 0; i < zoo.size(); i++) {
                Document animal = zoo.get(i);
                String emoji = (String) animal.get("emoji");
                if (emoji.equals("<:penguinzoo:842208725165932584>")) emoji = ":penguin:";
                else if (emoji.equals("<:transparent_jellybot:833491227995013130>")) emoji = ":fish_cake:";
                else if (emoji.equals("<:blobfish:803731505071390791>")) emoji = ":fish:";
                String end = (i + 1) % 5 == 0 ? "\n" : " ";
                toSend = toSend + emoji + end;
            }
            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(user)
                    .setTitle("Minco Zoo")
                    .setColor(new Color(244, 208, 63))
                    .setDescription(toSend)
                    .setFooter(channelName);
            event.getChannel().sendMessage(embed);
        }
    }
}