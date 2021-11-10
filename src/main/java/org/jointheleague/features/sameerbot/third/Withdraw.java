package org.jointheleague.features.sameerbot.third;

import com.mongodb.client.model.Updates;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Withdraw extends Feature {

    public final String COMMAND = "!withdraw";
    Client client;

    public Withdraw(String channelName, Client client) {
        super(channelName);
        this.client = client;

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Withdraw money from your bank into your wallet"
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
            int userBank = (int) client.findOne(id).get("bank");
            if (depositAmount > userBank) {
                event.getChannel().sendMessage("You don't have that amount of money to withdraw");
                return;
            }

            client.findOneAndUpdate(id, Updates.inc("mincoDollars", depositAmount));
            client.findOneAndUpdate(id, Updates.inc("bank", -depositAmount));
            event.getChannel().sendMessage("You withdrew " + depositAmount + " md from your bank");
        }
    }
}
