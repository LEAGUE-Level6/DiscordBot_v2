package org.jointheleague.features;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discordbot.CustomMessageCreateListener;
import org.jointheleague.pojo.help_embed.HelpEmbed;

import java.util.Random;

public class HighLowGame extends CustomMessageCreateListener {

    public static final String COMMAND = "!highLow";
    private final Random random = new Random();
    private int numberToGuess;

    public HighLowGame(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "High low game.  The computer picks a number when you start the game, and then tells you if your current guess is too low or too high");
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            numberToGuess = random.nextInt(100) + 1;
            event.getChannel().sendMessage("I have picked a number between 1 and 100. Guess by using e.g. !highLow 5");
        }
        //check a guess
        else if (messageContent.contains(COMMAND) && !messageContent.contains("e.g.")) {

            //check if the game has been started
            if(numberToGuess == 0){
                //tell them to start the game first
                event.getChannel().sendMessage("Please start the game first using just the command");
                return;
            }

            //parse the guess from the message
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");

            System.out.println(guessMessage);
            //change the guess to an int
            int guess = 0;
            try{
                guess = Integer.parseInt(guessMessage);
            }
            catch(NumberFormatException e){
                //tell them to format their guess properly
                event.getChannel().sendMessage("Please format your guess like this: " + COMMAND + " 5");
                return;
            }

            if (guess < numberToGuess) {
                //tell them it's too low
                event.getChannel().sendMessage(guess + " is too low.  Guess again!");
            } else if (guess > numberToGuess) {
                //tell them it's too high
                event.getChannel().sendMessage(guess + " is too high.  Guess again!");
            } else {
                //they got it correct
                event.getChannel().sendMessage("Correct!  The number I picked was " + numberToGuess);
                //set numberToGuess back to 0
                numberToGuess = 0;
            }

        }
    }
}
