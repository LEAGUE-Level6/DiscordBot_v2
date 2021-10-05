package org.jointheleague.features.sameer_bot.second;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import java.util.ArrayList;

public class Buy extends Feature {
    public final String COMMAND = "!buy";
    public Buy(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Buy an item ... (options: milk)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String item = messageContent.substring(5);
            if (item.equals("")){
                event.getChannel().sendMessage("You did not specify an item");
                return;
            }
            String id = event.getMessageAuthor().getIdAsString();
            int index = -1;
            if (!Data.userToInventory.containsKey(id)) {
                Data.userToInventory.put(id, new ArrayList<>());
            }
            for (int i=0;i<Data.validItems.length;i++) {
                if (item.equalsIgnoreCase(Data.validItems[i])) {
                    index=i;
                }
            }
            if (index == -1) {
                event.getChannel().sendMessage("That item isn't in the shop");
                return;
            }
            int price = Data.prices[index];
            int money = Data.getMoney(event.getMessageAuthor().getIdAsString());
            if (money < price) {
                event.getChannel().sendMessage("You need :coin: " + price + " to buy this item.");
                return;
            }
            ArrayList<String> inv = Data.userToInventory.get(id);
            inv.add(item);
            Data.userToInventory.put(id, inv);
            Data.userToCoins.put(id, money-price);
            event.getChannel().sendMessage("You bought " + item + " for :coin: " + price);
        }
    }
}
