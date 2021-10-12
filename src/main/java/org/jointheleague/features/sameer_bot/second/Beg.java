package org.jointheleague.features.sameer_bot.second;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Date;
import java.util.HashMap;

public class Beg extends Feature {
    public HashMap<String, Integer> cooldowns = new HashMap<>();
    public final String COMMAND = "!beg";
    public final int COOLDOWN = 30000; // ms

    public Beg(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Get a random amount of coins"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            boolean y = handleCooldowns(event);
            if (y) return;
            String id = event.getMessageAuthor().getIdAsString();
            int moneyAmount = Data.getMoney(id);
            int increaseAmount = (int) Math.floor(Math.random()*10) + 1;
            moneyAmount += increaseAmount;
            Data.userToCoins.put(id, moneyAmount);
            event.getChannel().sendMessage(String.format("You received %d coins. You now have :coin: %d.", increaseAmount, moneyAmount));
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
