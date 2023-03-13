package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class calculator extends Feature {

    public final String COMMAND = "!calculate";

    public calculator(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A simple calculator for addition, subtraction, multiplication, and division"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            String equation = messageContent.substring(10);
            event.getChannel().sendMessage(equation);
            if (equation.contains("+")){
                String nums[] = equation.split("[+]");
                nums[0] = nums[0].trim();
                nums[1] = nums[1].trim();
                if (nums.length == 2 && !nums[0].contains("+") && !nums[0].contains("-") && !nums[0].contains("*") && !nums[0].contains("/") && !nums[1].contains("+") && !nums[1].contains("-") && !nums[1].contains("*") && !nums[1].contains("/") && !nums[0].contains(" ") && !nums[1].contains(" ")){
                    event.getChannel().sendMessage((Double.parseDouble(nums[0].split(" ")[0]) + Double.parseDouble(nums[1].split(" ")[0]))+"");
                }else {
                    event.getChannel().sendMessage("Illegal expression.  Please try again.");
                }
            }else if (equation.contains("-")){
                String nums[] = equation.split("[-]");
                nums[0] = nums[0].trim();
                nums[1] = nums[1].trim();
                if (nums.length == 2 && !nums[0].contains("+") && !nums[0].contains("-") && !nums[0].contains("*") && !nums[0].contains("/") && !nums[1].contains("+") && !nums[1].contains("-") && !nums[1].contains("*") && !nums[1].contains("/") && !nums[0].contains(" ") && !nums[1].contains(" ")){
                    event.getChannel().sendMessage((Double.parseDouble(nums[0].split(" ")[0]) - Double.parseDouble(nums[1].split(" ")[0]))+"");
                }else {
                    event.getChannel().sendMessage("Illegal expression.  Please try again.");
                }
            }else if (equation.contains("*")){
                String nums[] = equation.split("[*]");
                nums[0] = nums[0].trim();
                nums[1] = nums[1].trim();
                if (nums.length == 2 && !nums[0].contains("+") && !nums[0].contains("-") && !nums[0].contains("*") && !nums[0].contains("/") && !nums[1].contains("+") && !nums[1].contains("-") && !nums[1].contains("*") && !nums[1].contains("/") && !nums[0].contains(" ") && !nums[1].contains(" ")){
                    event.getChannel().sendMessage((Double.parseDouble(nums[0].split(" ")[0]) * Double.parseDouble(nums[1].split(" ")[0]))+"");
                }else {
                    event.getChannel().sendMessage("Illegal expression.  Please try again.");
                }
            }else if (equation.contains("/")){
                String nums[] = equation.split("[/]");
                nums[0] = nums[0].trim();
                nums[1] = nums[1].trim();
                if (nums.length == 2 && !nums[0].contains("+") && !nums[0].contains("-") && !nums[0].contains("*") && !nums[0].contains("/") && !nums[1].contains("+") && !nums[1].contains("-") && !nums[1].contains("*") && !nums[1].contains("/") && !nums[0].contains(" ") && !nums[1].contains(" ")){
                    event.getChannel().sendMessage((Double.parseDouble(nums[0].split(" ")[0]) / Double.parseDouble(nums[1].split(" ")[0]))+"");
                }else {
                    event.getChannel().sendMessage("Illegal expression.  Please try again.");
                }
            }else{
                event.getChannel().sendMessage("bad");
            }
        }
    }

}
