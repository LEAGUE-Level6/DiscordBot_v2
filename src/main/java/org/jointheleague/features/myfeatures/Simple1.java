package org.jointheleague.features.myfeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Simple1 extends Feature {

    public final String COMMAND = "!greeting";
    Random random = new Random();

    public Simple1(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Greets you");
    }

    @Override
    public void handle(MessageCreateEvent event) {
//        int num = random.nextInt(5);
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
//            if(num == 1){
                event.getChannel().sendMessage("Hello!");
//
//           else  if(num == 2){
//                event.getChannel().sendMessage("G'day mate");
//            }
//           else if(num == 3){
//                event.getChannel().sendMessage("howdy");
//            }
//          else if(num == 4){
//                event.getChannel().sendMessage("Ahoy!");
//            }

        }
    }

}
