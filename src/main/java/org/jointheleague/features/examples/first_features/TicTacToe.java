package org.jointheleague.features.examples.first_features;
import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class TicTacToe extends Feature{
    public final String COMMAND = "!TicTacToe";
    private final Random random = new Random();


    public TicTacToe(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Play tic tac toe with a bot"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {


            event.getChannel().sendMessage("Enter commands and try to get rich");
        } else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")) {




        }
    }

}

