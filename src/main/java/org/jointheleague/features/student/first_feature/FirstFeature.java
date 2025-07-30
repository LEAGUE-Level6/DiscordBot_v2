package org.jointheleague.features.student.first_feature;


import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class FirstFeature extends Feature {

    public final String COMMAND = "!riddle";
    public final String ANSWERCOMMAND = "!riddleanswer";
    boolean riddleSent = false;

    String[] generatedRiddle;

    public FirstFeature(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Simple command, when called produced a mind-boggling riddle for the user to ponder. \n\nAnswer provided with " + ANSWERCOMMAND
        );

    }

    @Override
    public void handle(ReceivedMessage event) {


        String messageContent = event.getMessageContent();


        if (messageContent.startsWith(COMMAND) && !messageContent.contains(ANSWERCOMMAND)&&!riddleSent) {
            Random r = new Random();
            int rand = r.nextInt(10);
            generatedRiddle = generateRiddle(rand);
            //respond to message here
            event.sendResponse("Here is your riddle:\n" + generatedRiddle[0] + "\nType '" + ANSWERCOMMAND + "' to get the answer!");
            riddleSent = true;
        }
        else if (messageContent.startsWith(ANSWERCOMMAND) && riddleSent) {
            event.sendResponse("The answer to your riddle is:\n" + generatedRiddle[1]);
            riddleSent = false;

        }
        else if (messageContent.startsWith(ANSWERCOMMAND)  && !riddleSent ){
            event.sendResponse("You have to ask for a riddle first!");
        }
        else{
            return;
        }
    }

    public String[] generateRiddle(int r) {
        String[] riddle = new String[2];
        int rand = r;

        switch (rand) {
            case 0:
                riddle[0] = "What is always on the ground but never dirty?";
                riddle[1] = "A shadow";

                break;
            case 1:
                riddle[0] = "What can fill a room without taking up any space?";
                riddle[1] = "Light";

                break;
            case 2:
                riddle[0] = "What do you bury alive, but dig up dead?";
                riddle[1] = "A plant";

                break;
            case 3:
                riddle[0] = "I am always old, but sometimes also new. While I'm never sad, sometimes I am blue. \nI am never empty, but only sometimes full. I never push, but I always pull. What am I?";
                riddle[1] = "The moon";

                break;
            case 4:
                riddle[0] = "If you give me a drink, I die, but if you feed me I grow, what am I?";
                riddle[1] = "Fire";

                break;
            case 5:
                riddle[0] = "What can go up but can never come down?";
                riddle[1] = "Your age";

                break;
            case 6:
                riddle[0] = "What word in the dictionary is spelled incorrectly?";
                riddle[1] = "Incorrectly";

                break;
            case 7:
                riddle[0] = "What occurs once in a minute, twice in a moment, and never in 1000 years?";
                riddle[1] = "The letter 'M'";

                break;
            case 8:
                riddle[0] = "What is so fragile that saying its name breaks it?";
                riddle[1] = "Silence";

                break;
            case 9:
                riddle[0] = "What has 13 hearts but no other organs?";
                riddle[1] = "A deck of cards";
                break;

        }
        ;

        return riddle;
    }

}
