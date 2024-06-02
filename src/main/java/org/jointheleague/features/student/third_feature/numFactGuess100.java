package org.jointheleague.features.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.util.Locale;
import java.util.Random;

public class numFactGuess100 extends Feature {

    public final String COMMAND = "!numFactGuess100";
    int gameStart = 1;
    String currentNum;
    int funNum;

    public numFactGuess100(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command gives you a random fact and you have to guess which number from 0-100 is the correct number applied. Formatted like (!numFactGuess100)");
    }
    //IGNORE
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if(messageContent.startsWith(COMMAND)) {
            gameStart = 1;
            Random rand = new Random();
            funNum = rand.nextInt(101);
            ApiExample numApi = new ApiExample();
            String numFact = numApi.findNumFact("" + funNum);
            numFact = numFact.replace(funNum + " is ", "");
            String tmp = numFact.substring(0, 1).toUpperCase() + numFact.substring(1);
            event.getChannel().sendMessage("Question: (Write !STOP anytime to get the answer) \n" + "``` " + tmp + " ```");
            currentNum = "" + funNum;
        } else if(!messageContent.contains(COMMAND) && !messageContent.contains("Question") && !messageContent.contains("correct")) {
            if(gameStart == 0) {
                return;
            }
            if(messageContent.contains("!STOP")) {
                gameStart = 0;
                event.getChannel().sendMessage("Thanks for playing! The correct number was " + funNum);
                return;
            }
            if(currentNum.equals(messageContent)) {
                event.getChannel().sendMessage("CORRECT!!!");
                gameStart = 0;
            } else {
                event.getChannel().sendMessage("Incorrect! Try again");
            }
        }
    }

}

