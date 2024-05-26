package org.jointheleague.features.GooBotFeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.lang.Math;

public class Calculator extends Feature {

    public final String COMMAND = "!calculator";

    public Calculator(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(COMMAND, "Give GooBot a number, an operation symbol, and" +
                "another number, and GooBot will perform the operation for you! This includes: +, x, /, " +
                "-, ^, %. Please have no spaces in the operation provided. Have fun! Eg: !Calculator 55,+,12");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        int num1;
        String op;
        int num2;
        //respond to message here
        if (messageContent.startsWith(COMMAND)) {
            int index1 = messageContent.indexOf(",");
            int index2 = messageContent.indexOf(",", 15);
            String sub1 = messageContent.substring(12, index1);
            String subOp2 = messageContent.substring(index1 + 1, index2);
            String sub2 = messageContent.substring(index2 + 1);
            num1 = Integer.parseInt(sub1);
            num2 = Integer.parseInt(sub2);
            switch (subOp2) {
                case "+":
                    int sum = num1 + num2;
                    event.getChannel().sendMessage("Your sum is: " + sum);
                    break;

                case "-":
                    int difference = num1 - num2;
                    event.getChannel().sendMessage("Your difference is: " + difference);
                    break;

                case "/":
                    int quotient = num1 / num2;
                    event.getChannel().sendMessage("Your quotient is: " + quotient);
                    break;

                case "x":
                    int product = num1 * num2;
                    event.getChannel().sendMessage("Your product is: " + product);
                    break;

                case "^":
                    double result = Math.pow(num1, num2);
                    event.getChannel().sendMessage("Your result is: " + result);
                    break;

                case "%":
                    int remainder = num1 % num2;
                    event.getChannel().sendMessage("Your remainder is: " + remainder);
                    break;

                default:
                    event.getChannel().sendMessage("Sorry, I couldn't understand your request." +
                            "Please try again.");
                    break;
            }
        }
    }
}

