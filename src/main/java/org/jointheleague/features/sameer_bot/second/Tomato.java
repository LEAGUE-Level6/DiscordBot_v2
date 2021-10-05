package org.jointheleague.features.sameer_bot.second;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class Tomato extends Feature {
    public final String COMMAND = "!tomato";

    public Tomato(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Use your tomato"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String id = event.getMessageAuthor().getIdAsString();
            if (!Data.userToInventory.containsKey(id)) {
                Data.userToInventory.put(id, new ArrayList<>());
            }
            ArrayList<String> inventory = Data.userToInventory.get(id);
            if (!inventory.contains("tomato")) {
                event.getChannel().sendMessage("You don't have a tomato!");
                return;
            }
            ArrayList<String> inv = Data.userToInventory.get(id);
            inv.remove(inv.indexOf("tomato"));
            Data.userToInventory.put(id, inv);
            int giveAmount = new Random().nextInt(12 - 4) + 4;
            int money = Data.getMoney(id);
            Data.userToCoins.put(id, giveAmount + money);
            event.getChannel().sendMessage("You won :coin: " + giveAmount + " after you ate your tomato!");
        }
    }
}