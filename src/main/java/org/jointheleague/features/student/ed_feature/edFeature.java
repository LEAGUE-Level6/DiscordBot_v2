package org.jointheleague.features.student.ed_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;


public class edFeature extends Feature {
    Random rand = new Random();
    public final String COMMAND = "!roll";

    public edFeature(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This is Eddie's Feature. He hasn't decided what it is yet but it should at least do something.");
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();

        if (messageContent.contains(COMMAND)) {
            event.sendResponse("dice rolled");
            event.sendResponse(rollFeature(messageContent));
        }
    }

    private String rollFeature(String inputRoll) {
        inputRoll = inputRoll.replace(COMMAND, "");
        inputRoll = inputRoll.replaceAll(" ", "");
        String finalTotal = "";
        String[] dice = inputRoll.split("[+]");
        int finalVal = 0;
        int tempVal = 0;
        for (String nums : dice) {
            if (nums.contains("d")) {
                String[] toRoll = nums.toLowerCase().split("d");
                finalTotal += toRoll[0] + "d" + toRoll[1] + " rolled: ";
                for (int i = 0; i < Integer.parseInt(toRoll[0]); i++) {
                    tempVal = rand.nextInt(Integer.parseInt(toRoll[1]))+1;
                    finalTotal += tempVal + " ";
                    finalVal += tempVal;
                }
                finalTotal += "\n for a total of " + finalVal + "\n";

            } else {
                finalVal += Integer.parseInt(nums);
                finalTotal += "+ " + nums + "\n for a total of " + finalVal + "\n";
            }
        }
        return finalTotal;
    }
}
