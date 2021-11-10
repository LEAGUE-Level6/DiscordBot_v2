package org.jointheleague.features.sameerbot.third;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import com.mongodb.client.model.Updates;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Beg extends Feature {
    public HashMap<String, Integer> cooldowns = new HashMap<>();
    public final String COMMAND = "!beg";
    public final int COOLDOWN = 390000; // 6.5 minutes
    Client client;

    public Beg(String channelName, Client client) {
        super(channelName);
        this.client = client;

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Beg for Minco Dollars"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            if (handleCooldowns(event)) return;
            int amount = new Random().nextInt(4)+4; // 4 to 7 inclusive
            client.findOneAndUpdate(event.getMessageAuthor().getIdAsString(), Updates.inc("mincoDollars", amount));
            event.getChannel().sendMessage("You won " + amount + " Minco Dollars!");

        }
    }

    public boolean handleCooldowns(MessageCreateEvent event) {
        int current = (int) new Date().getTime();
        String id = event.getMessageAuthor().getIdAsString();
        if (cooldowns.containsKey(id)) {
            int expTime = cooldowns.get(id) + COOLDOWN;
            if (current < expTime) {
                double timeLeft = (double) expTime - current;
                event.getChannel().sendMessage("Please wait " + (timeLeft/1000) + " seconds before using !beg again.");
                return true;
            }
        }
        cooldowns.put(id, current);
        return false;
    }

}

