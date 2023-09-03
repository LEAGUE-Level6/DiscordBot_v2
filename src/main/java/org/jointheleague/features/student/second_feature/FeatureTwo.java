package org.jointheleague.features.student.second_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

public class FeatureTwo extends FeatureTemplate {
    public final String COMMAND = "!popquiz";
    int questionNumber = 0;
    int points = 0;

    public FeatureTwo(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command will give you a short quiz about science and science related facts. Remember to answer the question type "+ COMMAND+" and then a space following that and finally your answer from the list."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        if (messageContent.equals(COMMAND)) {
            //respond to message here
            points = 0;
            questionNumber = 1;
            event.getChannel().sendMessage("First question: What is it called when a liquid's temperature is lowered until it is below its freezing point yet it is still a liquid? \nA: Ice Nucleation \nB: Supercooling \nC: Antifreezing \nD: Impossible"
                     );
        }
        else if(messageContent.contains(COMMAND) && questionNumber == 0){
            event.getChannel().sendMessage("Please be sure to start the game before adding any answer prompt. Start the game by typing !\u200Epopquiz.");
        }
        else if (messageContent.contains(COMMAND) && questionNumber == 1){
           if(messageContent.equalsIgnoreCase(COMMAND+" Supercooling") || messageContent.equalsIgnoreCase(COMMAND+" B")){
               points++;
               event.getChannel().sendMessage("Correct! Adding one point to your score. Alright onto the next question!");
               questionNumber++;
               event.getChannel().sendMessage("Question two: What is the most radioactive substance ever discovered? \n A: Uranium \n B: Plutonium \n C: Radium \n D: Polonium \n E: Nobelium \n F: Lawrencium \n G: Oganesson \n H: Krypton \n I: Promethium \n J: Iridium" );
           }
           else{
               event.getChannel().sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Alright onto the next question!");
               questionNumber++;
               event.getChannel().sendMessage("Question two: What is the most radioactive substance ever discovered? \n A: Uranium \n B: Plutonium \n C: Radium \n D: Polonium \n E: Nobelium \n F: Lawrencium \n G: Oganesson \n H: Krypton \n I: Promethium \n J: Iridium" );
           }
        }
        else if (messageContent.contains(COMMAND) && questionNumber ==2){
            if(messageContent.equalsIgnoreCase(COMMAND + " Oganesson") || messageContent.equalsIgnoreCase(COMMAND + " G")){
                points++;
                event.getChannel().sendMessage("Correct! Adding one point to your score. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("Question three: Can any element in any state be magnetic? \nA: Yes, all elements can be magnetic and it can occur naturally in each. \nB: Yes, (almost) all elements can be magnetic with electromagnetism. \nC: No, not all elements can be magnetic under any circumstances.");
            }
            else{
                event.getChannel().sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("Question three: Can any element in any state be magnetic? \nA: Yes, all elements can be magnetic and it can occur naturally in each. \nB: Yes, (almost) all elements can be magnetic with electromagnetism. \nC: No, not all elements can be magnetic under any circumstances.");
            }
        }
        else if (messageContent.contains(COMMAND) && questionNumber ==3){
            if(messageContent.equalsIgnoreCase(COMMAND + " Yes, (almost) all elements can be magnetic with electromagnetism.") || messageContent.equalsIgnoreCase(COMMAND + " B")){
                points++;
                event.getChannel().sendMessage("Correct! Adding one point to your score. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("Question four: What is chalk made out of? \nA: Nothing,chalk is chalk \nB: Limestone, which is made up of Calcite \nC: Calcium Carbonate \nD: Calcium Citrate");
            }
            else{
                event.getChannel().sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("Question four: What is chalk made out of? \nA: Nothing,chalk is chalk \nB: Limestone, which is made up of Calcite \nC: Calcium Carbonate \nD: Calcium Citrate");
            }
        }
        else if (messageContent.contains(COMMAND) && questionNumber ==4) {
            if (messageContent.equalsIgnoreCase(COMMAND + " Limestone, which is made up of Calcite") || messageContent.equalsIgnoreCase(COMMAND + " B")) {
                points++;
                event.getChannel().sendMessage("Correct! Adding one point to your score. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("What is the only tree to grow in saltwater? \nA: Mangrove \nB: Banyan \nC: Bald cypress \nD: Water tupelo");
            }
            else {
                event.getChannel().sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Alright onto the next question!");
                questionNumber++;
                event.getChannel().sendMessage("What is the only tree to grow in saltwater? \nA: Mangrove \nB: Banyan \nC: Bald cypress \nD: Water tupelo");
            }
        }
        else if (messageContent.contains(COMMAND) && questionNumber ==5) {
            if (messageContent.equalsIgnoreCase(COMMAND + " Mangrove") || messageContent.equalsIgnoreCase(COMMAND + " A")) {
                points++;
                event.getChannel().sendMessage("Correct! Adding one point to your score. Alright onto the next question!");
                event.getChannel().sendMessage("Your final score is " + points+" points!");
                questionNumber=0;
            }
            else{
                event.getChannel().sendMessage("Ooh, so close. Sadly we cannot give points for incorrect answers. Alright onto the next question!");
                event.getChannel().sendMessage("Your final score is " + points+" points!");
                questionNumber=0;
            }
        }

    }
}
