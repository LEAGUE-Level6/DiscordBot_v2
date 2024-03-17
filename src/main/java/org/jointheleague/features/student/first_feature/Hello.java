package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Hello extends Feature{
    public final String COMMAND = "!hello";
    Random ran = new Random();
    int responseNum;

    public Hello(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "The bot will respond with hello in a random language."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            responseNum = ran.nextInt(5);
            switch(responseNum){
                case 0:
                    event.getChannel().sendMessage("Hello");
                    break;
                case 1:
                    event.getChannel().sendMessage("Bonjour");
                    break;
                case 2:
                    event.getChannel().sendMessage("Hola");
                    break;
                case 3:
                    event.getChannel().sendMessage("Ciao");
                    break;
                case 4:
                    event.getChannel().sendMessage("Shalom");
                    break;
            }

        }
    }
}
