package org.jointheleague.features.myfeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class UpdatedHighLow extends Feature {

    public final String COMMAND = "!game";
    final Random random = new Random();

    int numberToGuess;
    int lives;
    boolean isPlaying;
    int max;

    public UpdatedHighLow(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Choose from 3 levels and play a guessing game!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        //all variables init here
        String messageContent = event.getMessageContent();


        //starting game with command
        if (messageContent.contentEquals(COMMAND)) {
            event.getChannel().sendMessage("What level of difficulty will you choose? Easy, medium, or hard? !game easy  for easy level");

        }
        //CHOOSING DIFFICULTY LEVEL!!
        else if (messageContent.startsWith(COMMAND) && (messageContent.contains("easy") || messageContent.contains("medium") || messageContent.contains("hard"))) {
            if (messageContent.contains("easy") && isPlaying == false) {
                isPlaying = true;
                numberToGuess = random.nextInt(20) + 1;
                lives = 8;
                max = 20;
                event.getChannel().sendMessage("EASY mode selected. I have selected a random integer from 1-20 inclusive. Use e.g. !game 2 to guess.");
                event.getChannel().sendMessage("You get 8 tries to beat me.");
            }
            if (messageContent.contains("medium")) {
                isPlaying = true;
                numberToGuess = random.nextInt(100) + 1;
                lives = 8;
                max = 100;
                event.getChannel().sendMessage("MEDIUM mode selected. I have selected a random integer from 1-100 inclusive. Use e.g. !game 2 to guess.");
                event.getChannel().sendMessage("You get 8 tries to beat me.");
            }
            if (messageContent.contains("hard")) {
                isPlaying = true;
                numberToGuess = random.nextInt(1000) + 1;
                lives =  12;
                max = 1000;

                event.getChannel().sendMessage("HARD mode selected. I have selected a random integer from 1-1,000 inclusive. Use e.g. !game 2 to guess.");
                event.getChannel().sendMessage("You get 12 tries to beat me.");
            }
        }
        else if(messageContent.startsWith(COMMAND) && isPlaying){
           String stringGuess =  messageContent.split(" ")[1];

           try{
               int userGuess = Integer.parseInt(stringGuess);
               if((userGuess > max) && lives > 0){
                   event.getChannel().sendMessage("doofus. you're out of range. " + max + " is the highest you can go");
                   lives --;
               }
               else if((userGuess == numberToGuess) && lives > 0){
                   event.getChannel().sendMessage("Congrats! You guessed correctly!!");
               }
               else if((userGuess < numberToGuess) && lives > 0){
                   event.getChannel().sendMessage("Too low.");
                   lives --;
                   event.getChannel().sendMessage("You have " + lives + " lives left.");
               }
               else if((userGuess > numberToGuess) && lives > 0){
                   event.getChannel().sendMessage("Too high.");
                   lives --;
                   event.getChannel().sendMessage("You have " + lives + " lives left.");
               }

           }catch(NumberFormatException e){
               event.getChannel().sendMessage("Not a valid thing");
           }
        }

    }
}



