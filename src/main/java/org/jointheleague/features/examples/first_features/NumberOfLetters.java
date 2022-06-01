package org.jointheleague.features.examples.first_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class NumberOfLetters extends Feature {

    public final String COMMAND = "!letters";
    private final Random random = new Random();
    int numberOfLetters;
    String letters="";
   

    public NumberOfLetters(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Letters game.  The computer displays a certain amount of letters and you have to guess how many letters there are. The bot will tell you whether you are above or below the correct number.");
    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();

        if (messageContent.equals(COMMAND)) {
            numberOfLetters = random.nextInt(500) + 50;
            for (int i = 0; i < numberOfLetters; i++) {
				letters=letters+"x";
			}
            event.getChannel().sendMessage("guess how many letters are in this: "+letters);
        }
        else if (messageContent.contains(COMMAND)) {
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
            int guess=0;
            try{
                guess = Integer.parseInt(guessMessage);
            }
            catch(NumberFormatException e){
                event.getChannel().sendMessage("Please format your guess like this: " + COMMAND + " 5");
                return;
            }
            if (guess < numberOfLetters) {
                event.getChannel().sendMessage(guess + " is too low.  Guess again!");
            } else if (guess > numberOfLetters) {
                event.getChannel().sendMessage(guess + " is too high.  Guess again!");
            } else {
                event.getChannel().sendMessage("Correct!  The number of letters are " + numberOfLetters);
                numberOfLetters = 0;
            }
        }
    }
}
