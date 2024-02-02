package org.jointheleague.features.student.second_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!story";
    int storyPart = 0;
    int gameStart = 1;
    public FeatureTwo(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command allows you to interact with a short interactive story."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("Welcome! This is an interactive story. You will be presented with two options and must choose by writing '!story CHOICE' \n Will you AWAKEN or SLEEPIN");
            gameStart = 1;
        }
        else if(messageContent.contains(COMMAND) && !messageContent.contains("interactive story")){
            storyPart++;
            if(gameStart == 0) {
                event.getChannel().sendMessage("You need to start the game first by using '!story'");
            }
            if(storyPart==1) {
                event.getChannel().sendMessage("Stuff happens based on choice (For next class)");
            }
        }
    }

}
