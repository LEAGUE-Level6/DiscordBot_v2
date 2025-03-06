package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class ChillGame extends Feature {

    public final String COMMAND = "!chillGame";
    String wordToGuess = "";
    String currentDisplay = "";

    public ChillGame(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This is a pretty chill game, that's fun to play. It'll be pretty chill."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            if (messageContent.equals(COMMAND)){
                event.sendResponse("There is a word you need to guess, guess a letter by doing the command, then a letter. Ex: \"!chillGame e\"");
                wordToGuess = Utilities.readRandomLineFromFile("dictionary.txt");
                event.sendResponse("DEBUG: " + wordToGuess);
                System.out.println(wordToGuess);
            }
        }
    }

}
