package org.jointheleague.features.student.ed_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;


public class edFeature extends Feature{
    Random rand = new Random();
    public final String COMMAND = "!edFeature";

    public edFeature (String channelName){
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This is Eddie's Feature. He hasn't decided what it is yet but it should at least do something.");
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();

        if( messageContent.contains(COMMAND)){
            event.sendResponse("yup! Eddie's Feature can do stuff!");
        }
    }

    private String rollFeature(String inputRoll){
        inputRoll.replace(COMMAND, "");
        String finalTotal = "";
        String[] dice = inputRoll.split("[+-]");
        for(String nums : dice){
                String[] toRoll = nums.toLowerCase().split("d");
                finalTotal += nums + " rolled: " + rand.nextInt(Integer.parseInt(toRoll[1])) + "\n";
                
           }
        return finalTotal;
    }

    }
