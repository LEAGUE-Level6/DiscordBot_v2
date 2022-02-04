package org.jointheleague.features.student.second_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;


public class RockPaperScissors extends Feature {
    //command
    public final String COMMAND = "!rps";

    public RockPaperScissors(String channelName) {
        super(channelName);
        //what shows with !help
        helpEmbed = new HelpEmbed(COMMAND, "Play a game of Rock Paper Scissors! Put your action after the command, e.g. " + COMMAND + " rock");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase();
        Random rand = new Random();
        // 0: Rock
        // 1: Paper
        // 2: Scissors
        int compChoice = rand.nextInt(3);
        int playerChoice = -1;

        if (messageContent.contains(COMMAND) && !messageContent.contains("e.g.")) {

            //setting playerChoice or telling user errors
            if (messageContent.contains("rock")) {
                //rock
                playerChoice = 0;
            } else if (messageContent.contains("paper")) {
                //paper
                playerChoice = 1;
            } else if (messageContent.contains("scissors")) {
                //scissors
                playerChoice = 2;
            } else if (messageContent.equals(COMMAND)) {
                //no syntax
                event.getChannel().sendMessage("Make sure to put your action to play! e.g. " + COMMAND + " rock");
            } else {
                //bad spelling
                event.getChannel().sendMessage("Sorry, something went wrong, try again. Check your spelling!");
            }

            //rock
            if (compChoice == 0) {
                if (playerChoice == 0) {
                    //rock [tie]
                    event.getChannel().sendMessage("I choose rock! We tied! Play again?");
                }
                if (playerChoice == 1) {
                    //paper [lose]
                    event.getChannel().sendMessage("I choose rock! I lost! Play again?");
                }
                if (playerChoice == 2) {
                    //scissors [win]
                    event.getChannel().sendMessage("I choose rock! I won! Play again?");
                }
            }
            //paper
            else if (compChoice == 1) {
                if (playerChoice == 0) {
                    //rock [win]
                    event.getChannel().sendMessage("I choose paper! I won! Play again?");
                }
                if (playerChoice == 1) {
                    //paper [tie]
                    event.getChannel().sendMessage("I choose paper! We tied! Play again?");
                }
                if (playerChoice == 2) {
                    //scissors [lose]
                    event.getChannel().sendMessage("I choose paper! I lost! Play again?");
                }
            }
            //scissors
            else if (compChoice == 2) {
                if (playerChoice == 0) {
                    //rock [lose]
                    event.getChannel().sendMessage("I choose scissors! I lost! Play again?");
                }
                if (playerChoice == 1) {
                    //paper [win]
                    event.getChannel().sendMessage("I choose scissors! I won! Play again?");
                }
                if (playerChoice == 2) {
                    //scissors [tie]
                    event.getChannel().sendMessage("I choose scissors! We tied! Play again?");
                }
            }
            //something went wrong oops
            else {
                event.getChannel().sendMessage("Sorry, something went wrong. It's my fault! Try again.");
            }
        }
    }

}
