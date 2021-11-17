package org.jointheleague.features.sameerbot.third;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.ArrayList;

public class Buy extends Feature {

    public final String COMMAND = "!buy";
    Client client;

    public Buy(String channelName, Client client) {
        super(channelName);
        this.client = client;
        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "!buy <item name> Options: *ring*  (75 md), *crown* (900 md), *cowboy hat* (25 md), *candy* (33 md)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String id = event.getMessageAuthor().getIdAsString();
            String item;
            try {
                item = messageContent.substring(5);
            } catch (StringIndexOutOfBoundsException e) {
                event.getChannel().sendMessage("You didn't enter an item");
                return;
            }
            switch (item) {
                case "ring": {
                    try {
                        updateUser(id, "01", 75);
                        event.getChannel().sendMessage("You bought a **ring :ring:** for 75 md");
                    } catch (NotEnoughMoneyException e) {
                        event.getChannel().sendMessage("You need 75 md to buy a ring");
                    } catch (AlreadyHaveItemException e) {
                        event.getChannel().sendMessage("You already have a ring!");
                    }
                    break;
                }
                case "crown": {
                    try {
                        updateUser(id, "02", 900);
                        event.getChannel().sendMessage("You bought a **diamond crown :diamond_shape_with_a_dot_inside:** for 900 md");
                    } catch (NotEnoughMoneyException e) {
                        event.getChannel().sendMessage("You need 900 md to buy a diamond crown");
                    } catch (AlreadyHaveItemException e) {
                        event.getChannel().sendMessage("You already have a crown!");
                    }
                    break;
                }
                case "cowboy hat": {
                    try {
                        updateUser(id, "03", 25);
                        event.getChannel().sendMessage("You bought a **cowboy hat :cowboy:** for 25 md");
                    } catch (NotEnoughMoneyException e) {
                        event.getChannel().sendMessage("You need 25 md to buy a cowboy hat");
                    } catch (AlreadyHaveItemException e) {
                        event.getChannel().sendMessage("You already have a cowboy hat!");
                    }
                    break;
                }
                case "candy": {
                    try {
                        updateUser(id, "05", 33);
                        event.getChannel().sendMessage("You bought a candy for 33 md");
                    } catch (NotEnoughMoneyException e) {
                        event.getChannel().sendMessage("You need 33 md to buy a candy");
                    } catch (AlreadyHaveItemException e) {
                        event.getChannel().sendMessage("You already have a candy!");
                    }
                    break;
                }
                default: {
                    event.getChannel().sendMessage("You entered an invalid item");
                    return;
                }
            }
        }
    }

    public void updateUser(String id, String inventoryPush, int cost) throws NotEnoughMoneyException, AlreadyHaveItemException {
        Document doc = client.findOne(id);
        int userMoney = (int) doc.get("mincoDollars");
        ArrayList<String> inventory = (ArrayList<String>) doc.get("inventory");
        if (userMoney < cost) {
            throw new NotEnoughMoneyException();
        }
        if (inventory.contains(inventoryPush)) {
            throw new AlreadyHaveItemException();
        }
        client.findOneAndUpdate(id, Updates.push("inventory", inventoryPush));
        client.findOneAndUpdate(id, Updates.inc("mincoDollars", -cost));
    }
}

class NotEnoughMoneyException extends Exception {
}

class AlreadyHaveItemException extends Exception {
}