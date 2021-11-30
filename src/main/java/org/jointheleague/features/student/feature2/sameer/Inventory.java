package org.jointheleague.features.student.feature2.sameer;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;

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
            String id = event.getMessageAuthor().getIdAsString();
            if (!Data.userToInventory.containsKey(id)) {
                Data.userToInventory.put(id, new ArrayList<>());
            }
            ArrayList<String> inventory = Data.userToInventory.get(id);
            if (inventory.isEmpty()) {
                event.getChannel().sendMessage("Your inventory is empty");
            } else {
                event.getChannel().sendMessage(Data.formatItems(inventory));
            }
        }
    }
}
