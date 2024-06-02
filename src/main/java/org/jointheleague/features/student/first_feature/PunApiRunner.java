package org.jointheleague.features.student.first_feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class PunApiRunner extends Feature{
    public final String COMMAND = "!pun";

    public PunApiRunner(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                COMMAND,
                "Returns a random pun from the web. NOT guaranteed to be funny."
        );
    }

    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            System.out.println("One");
            PunApi punApi = new PunApi();
            System.out.println("Two");
            String pun = punApi.getPun();
            System.out.println("Three");
            event.getChannel().sendMessage(pun);
        }
    }


}
