package org.jointheleague.features.examples.first_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class BasicCookieFeature extends Feature{


        public final String COMMAND = "!Cookie";

        public BasicCookieFeature(String channelName) {
            super(channelName);

            //Create a help embed to describe feature when !help command is sent
            helpEmbed = new HelpEmbed(
                    COMMAND,
                    "Its a cookie"
            );
        }

        @Override
        public void handle(MessageCreateEvent event) {
            String plainCookie= "https://static.wikia.nocookie.net/cookieclicker/images/b/bb/Plain_cookies.png/revision/latest/scale-to-width-down/30?cb=20160217140558";
            String messageContent = event.getMessageContent();
            if (messageContent.equals(COMMAND)) {
                //respond to message here
                event.getChannel().sendMessage("Cookie");
                event.getChannel().sendMessage(plainCookie);
            }
        }

    }


