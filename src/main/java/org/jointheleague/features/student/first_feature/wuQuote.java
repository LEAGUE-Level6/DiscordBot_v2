package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.Random;

public class wuQuote extends FeatureTemplate {
    public final String COMMAND = "!wuQuote";

    public wuQuote(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command prints a wise quote from Master Wu."
        );
    }
//IGNORE
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            String[] list = {"'Never put off until tomorrow, what can be done today.'",
                    "'What is weird? Someone who is different, or someone who is different from you?",
                    "'Ninja never quit.'",
                    "'If you want something bad enough, you find a way to make it happen.'",
                    "'Lost? Hm. An interesting word. Sometimes one has to be lost in order to be found.'",
                    "'The best way to defeat your enemy is to make them your friend.'",
                    "'Focus on your adversary, no matter how insignificant. Make its strength your strength.'",
                    "'You are all my prized pupils, but none of us can do this alone.'"
            };
                Random r = new Random();
                event.sendResponse(list[r.nextInt(list.length-1)]);

            }

        }
    }







