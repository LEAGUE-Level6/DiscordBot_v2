package org.jointheleague.features.student.third_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.util.Random;

public class FeatureThree extends Feature {

    public final String COMMAND = "!numFact";
    public final String COMMAND2 = "!numFactGuess100";
    int gameStart = 1;

    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
            "This command gives you a random fact about a number. Formatted like (!numFact 1) or use (!numFactGuess100) to guess a number from the trivia fact!"
        );
    }
    //IGNORE
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND) && !messageContent.startsWith(COMMAND2)) {
            messageContent = messageContent
                    .replace(COMMAND, "")
                    .replace(" " , "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a number after the command (e.g. " + COMMAND + " 12)");
            }
            else {
                ApiExample numApi = new ApiExample();
                String numFact = numApi.findNumFact(messageContent);
                event.getChannel().sendMessage(numFact);
            }
        } /*else if(messageContent.startsWith(COMMAND2) && gameStart==1) {
            Random rand = new Random();
            int funNum = rand.nextInt(101);
            ApiExample numApi = new ApiExample();
            String numFact = numApi.findNumFact("" + funNum);
            numFact = numFact.replace(funNum + " is ", "");
            event.getChannel().sendMessage(numFact);
            String currentNum = "" + funNum;

            if(messageContent.contains("!STOP")) {
                gameStart = 0;
                event.getChannel().sendMessage("Thanks for playing!");
                return;
            }
            if(currentNum.equals(messageContent)) {
                qCount++;
                event.getChannel().sendMessage("CORRECT!!! Question " + qCount + " will begin shortly...");
                if(questions.size()==0) {
                    event.getChannel().sendMessage("Sorry but we're actually out of new questions for you! Good job getting so many correct!");
                }
                currentQ=setBlank;
                getRandQuestion();
                event.getChannel().sendMessage("Question " + qCount + ": " + currentQ.getContent());
            } else {
                event.getChannel().sendMessage("Incorrect! Try again");
            }
        }*/
    }

}

