package org.jointheleague.features.student.first_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Calculator extends Feature {

    public final String COMMAND = "!calculate";

    public Calculator(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Allows you to calculate any two numbers. You can choose from addition, subtraction, multiplication, and division. Format is " +
                "'!calculate OPERATION number1 number2)");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            messageContent = messageContent.replace("!calculate","");
            messageContent = messageContent.trim();
            String operation = "";
            for (int i = 0; i < messageContent.length(); i++) {
                if (messageContent.charAt(i) == ' '){
                    continue;
                }else {
                    operation += messageContent.charAt(i);
                }
            }
            messageContent = messageContent.replace(operation,"");
            messageContent = messageContent.trim();
            String num1 = "";
            for (int i = 0; i < messageContent.length(); i++) {
                if (messageContent.charAt(i) == ' '){
                    continue;
                }else {
                    num1 += messageContent.charAt(i);
                }
            }

            messageContent = messageContent.replace(num1,"");
            messageContent = messageContent.trim();
            String num2 = "";
            for (int i = 0; i < messageContent.length(); i++) {
                if (messageContent.charAt(i) == ' '){
                    continue;
                }else {
                    num2 += messageContent.charAt(i);
                }
            }
            switch(operation) {
                case "addition":
                    event.getChannel().sendMessage("The output of your calculation is " + (Integer.parseInt(num1) + Integer.parseInt(num2)));
                    break;
                case "subtraction":
                    event.getChannel().sendMessage("The output of your calculation is " + (Integer.parseInt(num1) - Integer.parseInt(num2)));
                    break;
                case "multiplication":
                    event.getChannel().sendMessage("The output of your calculation is " + (Integer.parseInt(num1) * Integer.parseInt(num2)));
                    break;
                case "division":
                    event.getChannel().sendMessage("The output of your calculation is " + (Integer.parseInt(num1) / Integer.parseInt(num2)));
                    break;
                default:
                    // code block
            }

        }
    }

}