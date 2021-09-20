package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;


public class MilkOnTheWall extends Feature {

    public final String COMMAND = "!milk";
    public final String COMMAND2 = "!setmilk";
    int milk = 10;


    public MilkOnTheWall(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Your favorite roadtrip chant in discord!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();


        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            if (milk <= 1) {
                event.getChannel().sendMessage("No more bottles on milk on the wall :(  -  Use !setmilk to add more!");
            } else {
                milk -= 1;
                event.getChannel().sendMessage(milk + " bottles on milk on the wall. " + milk + " bottles of milk! Take one down, pass it around, " + (milk - 1) + " bottles of milk on the wall!");
            }
        }

        //parse the guess from the message

        if(messageContent.startsWith(COMMAND2) ) {
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND2, "");

            //change the guess to an int

            int number = 0;
            try {
                number = Integer.parseInt(guessMessage);
                milk = number + 1;
                event.getChannel().sendMessage("Milk set to " + (milk - 1));
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("Invalid input! Use numbers...");
            }
        }


    }
    }
