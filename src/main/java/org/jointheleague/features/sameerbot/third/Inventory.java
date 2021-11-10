package org.jointheleague.features.sameerbot.third;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory extends Feature {

    public final String COMMAND = "!inventory";
    Client client;

    public Inventory(String channelName, Client client) {
        super(channelName);
        this.client = client;
        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "View your inventory"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            ArrayList<String> inventory = (ArrayList<String>) client.findOne(event.getMessageAuthor().getIdAsString()).get("inventory");
            if (inventory.size() == 0) {
                event.getChannel().sendMessage("You don't have any items in your inventory");
                return;
            }
            String toSend = "";
            for (String t : inventory) {
                if (t.equals("01")) toSend += ":ring: Marriage Ring\n";
                if (t.equals("02")) toSend += ":diamond_shape_with_a_dot_inside: Diamond Crown\n";
                if (t.equals("03")) toSend += ":cowboy: Cowboy Hat\n";
                if (t.equals("04")) toSend += ":tomato: Tomato\n";
                if (t.equals("05")) toSend += ":candy: Candy\n";
                if (t.equals("06"))
                    toSend += "Jellyfish\n";
                if (t.equals("07")) toSend += ":bear: Bear\n";
                if (t.equals("08")) toSend += ":cactus: Cactus\n";
                if (t.equals("09")) toSend += ":fire: Fire\n";
                if (t.equals("10")) toSend += "Lootbox\n";
                if (t.equals("11")) toSend += ":egg: Raw Egg\n";
                if (t.equals("11-0")) toSend += ":egg: Boiled Egg\n";
                if (t.equals("11-1")) toSend += ":egg: Scrambled Eggs\n";
                if (t.equals("11-2")) toSend += ":egg: Omelette\n";
                if (t.equals("12")) toSend += ":banana: Banana\n";
            };
            event.getChannel().sendMessage(toSend);
        }
    }

}
