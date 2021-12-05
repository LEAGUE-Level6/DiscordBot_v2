package org.jointheleague.discord_bot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class weather extends Feature {

    public final String COMMAND = "!rweather";

    public weather(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "random weather"
        );
    }
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            int num = (int)(Math.random()*4);
            if (num==0)event.getChannel().sendMessage("sunny");
            else if (num==1)event.getChannel().sendMessage("raining");
           else  if (num==2)event.getChannel().sendMessage("cloudy");
            else if (num==3)event.getChannel().sendMessage("snowing");

        }
    }

}
