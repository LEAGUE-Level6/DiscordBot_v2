package org.jointheleague.features.sameerbot.third;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Arrays;
import java.util.List;

public class Inventory extends Feature {

    public final String COMMAND = "!inventory";

    public Inventory(String channelName) {
        super(channelName);

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
            String[] inventory = (String[]) Client.findOne(event.getMessageAuthor().getIdAsString()).get("inventory");
            String[] newInventory = (String[]) Arrays.stream(inventory).map(t -> {
                if (t.equals("01")) return ":ring: Marriage Ring";
                if (t.equals("02")) return ":diamond_shape_with_a_dot_inside: Diamond Crown";
                if (t.equals("03")) return ":cowboy: Cowboy Hat";
                if (t.equals("04")) return ":tomato: Tomato";
                if (t.equals("05")) return ":candy: Candy";
                if (t.equals("06"))
                    return "Jellyfish";
                if (t.equals("07")) return ":bear: Bear";
                if (t.equals("08")) return ":cactus: Cactus";
                if (t.equals("09")) return ":fire: Fire";
                if (t.equals("10")) return "Lootbox";
                if (t.equals("11")) return ":egg: Raw Egg";
                if (t.equals("11-0")) return ":egg: Boiled Egg";
                if (t.equals("11-1")) return ":egg: Scrambled Eggs";
                if (t.equals("11-2")) return ":egg: Omelette";
                if (t.equals("12")) return ":banana: Banana";
                return null;
            }).toArray();
            event.getChannel().sendMessage(newInventory.toString());
        }
    }

}
