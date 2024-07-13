package org.jointheleague.features.GooBotFeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.lang.Math;

public class Calculator extends Feature {

    public final String COMMAND = "!calculator";
    public static double answer;

    public Calculator(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(COMMAND, "Give GooBot a number, an operation symbol, and" +
                "another number, and GooBot will perform the operation for you! This includes: +, x, /, " +
                "-, ^, %. Please have normal spacing in the operation provided. Have fun! Eg: !Calculator 55 + 12");
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
            int index2 = messageContent.indexOf(",", index1 + 1);
            String[] seperatedParts = messageContent.split(" ");
            if (seperatedParts.length > 3) {
                num1 = Integer.parseInt(seperatedParts[1]);
                num2 = Integer.parseInt(seperatedParts[3]);
                op = seperatedParts[2];
                answer = calculate(event, num1, num2, op);
                event.getChannel().sendMessage("I got the answer! It is... " + answer);
            } else {
                event.getChannel().sendMessage("Sorry, we've ran into an error. To be specific, the operation" +
                        " entered in seemed to not have properly processed, meaning you may have forgotten the correct amount" +
                        " of spaces or entered something else incorrect for the required format. Check in with the example" +
                        " at the !help section to make sure you entered your operation in correctly!");
            }
        }
    }

    public static double calculate(MessageCreateEvent event, double num1, double num2, String Op2) {
        answer = 0;
        switch (Op2) {
            case "+":
                answer = num1 + num2;
                break;

            case "-":
                answer = num1 - num2;
                break;

            case "/":
                answer = (double) num1 / num2;
                break;

            case "x":
                answer = num1 * num2;
                break;

            case "^":
                answer = Math.pow(num1, num2);
                break;

            case "%":
                answer = num1 % num2;
                break;

        }
        return answer;
    }
}

