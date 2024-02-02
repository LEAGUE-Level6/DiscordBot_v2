package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class FeatureOne extends FeatureTemplate {
    public final String COMMAND = "!artistRecc";

    public FeatureOne(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command gives you a new music artist to listen to."
        );
    }
//IGNORE
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Random ran = new Random();
            int ranInt = ran.nextInt(5);

            switch(ranInt){
                case 0:event.getChannel().sendMessage("Your new artist is: Miles Davis - Jazz");
                    break;
                case 1:event.getChannel().sendMessage("Your new artist is: The Beatles - Rock Pop");
                    break;
                case 2:event.getChannel().sendMessage("Your new artist is: Stevie Wonder - Funk, Soul");
                    break;
                case 3:event.getChannel().sendMessage("Your new artist is: Marvin Gaye - R&B, Soul");
                    break;
                case 4:event.getChannel().sendMessage("Your new artist is: Nujabes - Hip Hop");
                    break;
                default:event.getChannel().sendMessage("Your new artist is: Nirvana - Alternative Rock");
                    break;
            }

        }
    }
}
