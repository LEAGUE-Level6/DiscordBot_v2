package org.jointheleague.features.student.ed_feature;

import org.jointheleague.features.abstract_classes.Feature;

public class edFeature extends Feature{
    public final String COMMAND = "!edFeature";

    public edFeature (String channelName){
        super(channelName);
        helpEmbed = new helpEmbed(COMMAND, "This is Eddie's Feature. He hasn't decided what it is yet but it should at least do something.");
    }

    public void handle(RecievedMessage event){
        String messageContent = event.getMessageContent();

        if( messageContent.contains(COMMAND)){
            event.sendResponse("yup! Eddie's Feature can do stuff!");
        }
    }
}