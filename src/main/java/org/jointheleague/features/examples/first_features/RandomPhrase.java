package org.jointheleague.features.examples.first_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPhrase extends Feature {
    public final String COMMAND = "!phrase";

    public RandomPhrase (String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(COMMAND, "this gives you a random phrase");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            Random r = new Random();
            List<String> phrases = new ArrayList<>();
            phrases.add("cat got ya tongue?");
            phrases.add("im all ears");
            phrases.add("you're on thin ice");
            phrases.add("it'll cost you an arm and a leg");

            int num = r.nextInt(phrases.size()-1);
            event.getChannel().sendMessage(phrases.get(num));
        }
    }


}
