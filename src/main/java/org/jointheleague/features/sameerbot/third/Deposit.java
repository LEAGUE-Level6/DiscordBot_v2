package org.jointheleague.features.sameerbot.third;

import com.mongodb.client.model.Updates;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Deposit extends Feature {

    public final String COMMAND = "!deposit";
    Client client;

    public Deposit(String channelName, Client client) {
        super(channelName);
        this.client = client;
        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Deposit money from your wallet into your bank"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String moneyString = messageContent.substring(9);
            int depositAmount;
            try {
                depositAmount = Integer.parseInt(moneyString);
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("You sent an invalid number");
                return;
            }
            String id = event.getMessageAuthor().getIdAsString();
            int userMoney = (int) client.findOne(id).get("mincoDollars");
            if (depositAmount > userMoney) {
                event.getChannel().sendMessage("You don't have that amount of money to deposit");
                return;
            }

            client.findOneAndUpdate(id, Updates.inc("mincoDollars", -depositAmount));
            client.findOneAndUpdate(id, Updates.inc("bank", depositAmount));
            event.getChannel().sendMessage("You deposited " + depositAmount + " md into your bank");
        }
    }
}
