package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Compliment extends Feature {

    public final String COMMAND = "!compliment";

    public Compliment(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Receive a random compliment."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            String[] list = {"Your smile always brightens my day.", "Tu m'étonnes. Comme c'est magnifique!", "I love that shirt. It really bring out the color of your eyes.",
                    "Time spent with you is always worth it.", "Have you been working out? I can tell." ,
                    "I bet you solve crossword puzzles in ink.", "Is that a new perfume? It smells great.", "You are rocking those glasses!",
               "I bet you can make even the crankiest seniors smile.","Are you peanut butter, because you're making me jelly.",
            "I love that skirt on you! Can I buy it off of you for, say $20?", "You're so hot, I need a fan to cool me down.", "You're too kind. Maybe tone it down a bit",
                    "I bet you have straight A's in school.", "Your husband is a very lucky man.",
                    "I bet you got a 5 on the AP Chemistry exam.", "On my list of Top 10 Favorite People, you're definitely on there." };
            int rand = (int)(Math.random()*list.length)+0;
                event.getChannel().sendMessage(list[rand]);
        }
    }

}
