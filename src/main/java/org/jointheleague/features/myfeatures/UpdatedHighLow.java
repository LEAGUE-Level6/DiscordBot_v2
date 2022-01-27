package org.jointheleague.features.myfeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class UpdatedHighLow extends Feature {

    public final String COMMAND = "!game";

    public UpdatedHighLow(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Choose from 3 levels and play a guessing game!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        //all variables init here
       final Random random = new Random();
        int numberToGuess;


        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            event.getChannel().sendMessage("What level of difficulty will you choose? Easy, medium, or hard? !game easy  for easy level");

        }
        else if(messageContent.startsWith(COMMAND) && (messageContent.contains("easy") ||messageContent.contains("medium") || messageContent.contains("hard") )){
            if(messageContent.contains("easy")){
               // String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                numberToGuess = random.nextInt(20) + 1;
                event.getChannel().sendMessage("EASY mode selected.");
                event.getChannel().sendMessage("I have chosen an integer between 1-20 inclusive. You get 5 tries. Use e.g. !game 2 to play!");
            }

            if(messageContent.contains("medium")){
                numberToGuess = random.nextInt(100) + 1;
                event.getChannel().sendMessage("MEDIUM mode selected.");
                event.getChannel().sendMessage("I have chosen an integer between 1-100 inclusive. You get 10 tries. Use e.g. !game 2 to play!");
            }

            if(messageContent.contains("hard")){
                numberToGuess = random.nextInt(1000) + 1;
                event.getChannel().sendMessage("HARD mode selected.");
                event.getChannel().sendMessage("I have chosen an integer between 1-1,000 inclusive. You get 20 tries. Use e.g. !game 2 to play!");
            }
        }
        //easy, medium, vs hard difficulty
        //easy: 1-50, medium: 1-500, hard: -500-500
        //check numbers
    }

}

