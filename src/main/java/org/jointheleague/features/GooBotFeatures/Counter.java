package org.jointheleague.features.GooBotFeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Counter extends Feature {

    public final String COMMAND = "!counter";
    public int input = -1;
    public int counter = 0;
    public boolean gameRunning = true;

    public Counter(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(COMMAND, "Give GooBot a number and play a game where you will " +
                "type the first number and then GooBot will do the next, and so on until someone screws up!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        if (messageContent.startsWith(COMMAND)) {

            if (!messageContent.equalsIgnoreCase(COMMAND)) {
                String num = messageContent.substring(9);
                System.out.println(num);
                int messageNum = Integer.parseInt(num);
                Random ran = new Random();
                int whoStarts = ran.nextInt(2);
                System.out.println(whoStarts);


            }


            //respond to message here
            event.getChannel().sendMessage("Alright! Let's GOO!");
            event.getChannel().sendMessage("You start!");
            while (gameRunning) {
                game(event, whoStarts);
            }
        } else {
            event.getChannel().sendMessage("Alright! Let's GOO!");
            event.getChannel().sendMessage("I start!");
            while (gameRunning) {
                game(event, whoStarts);
            }
        }


    }

    public void game(MessageCreateEvent mce, int starter) {
        int counter = 0;
        String messageReceived = mce.getMessageContent();
        System.out.println("Content:" + messageReceived);
        int actual = Integer.parseInt(messageReceived);
        if (actual == counter) {
            mce.getChannel().sendMessage("Correct! Lets continue!");
            counter += 1;
            mce.getChannel().sendMessage(counter + "");
        } else {
            mce.getChannel().sendMessage("Incorrect! Game over!");
            gameRunning = false;
        }


    }
}



