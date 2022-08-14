package org.jointheleague.features.Feature_2;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class spam extends Feature {

    public final String COMMAND = "!spam";
    //public final String COMMAND2 = "!spam";
    public spam(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Do you enjoy spamming channels? I certainly do, which is why I created this feature so you can do it with minimal effort!" +
                        "Simply type in the command followed by a 0 with the number of times you want your message spammed! e.g: !spam nice #69. " +
                        "It pings everyone as well! (use with caution)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //Get the user's input
            //event.getChannel().sendMessage("e");

            try {
                String input = event.getMessageContent().substring(5, event.getMessageContent().lastIndexOf("0"));
                //System.out.println(input);
                //Get number of times the user wants this spammed
                String n = event.getMessageContent().substring(event.getMessageContent().lastIndexOf("0"));
                int times = Integer.parseInt(n);
                //For testing purposes
                //event.getChannel().sendMessage("Your input: " + input);
                // event.getChannel().sendMessage("Number of times: " + times);
                //Spam the message
                if (times < 0) {
                    event.getChannel().sendMessage("You can't have negative time. Go back to preschool and try again.");
                    return;
                } else {
                    for (int i = 0; i < times; i++) {
                        event.getChannel().sendMessage(input);
                    }
                    event.getChannel().sendMessage("Successfully spammed: " + event.getChannel() + "!");
                }

            }
            catch(Exception e){
                event.getChannel().sendMessage("Check the format of your message and try again, you bumbler! (Example: !spam e 069)");
            }

        }
    }

}
