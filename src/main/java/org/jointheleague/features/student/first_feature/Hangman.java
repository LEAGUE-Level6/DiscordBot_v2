package org.jointheleague.features.student.first_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Hangman extends Feature {

    public final String COMMAND = "!hangman";
    public final String COMM2 = "!guess";
    public String mystery;
    public String guessed;
    public String blank;
    public boolean play = false;
    public int lives;

    public Hangman(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Play a game of hangman with the computer. Can you best a machine in this world renowned battle of the wits?"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)&& play==false) {
            //respond to message here
            play = true;
            guessed = "";
            String[] words = {"memento", "federal", "executive", "abysmal", "oxygen", "elucidate", "burrito", "candid", "diplomat", "gratuitous", "feral", "holiday", "inclusive", "junta", "kimono", "negligible", "preclude", "laudable"};
            int rand = (int) (Math.random() * words.length) + 0;
             mystery = words[rand];
             lives = 7;
             blank = "";
            for (int i = 0; i < mystery.length(); i++) {
                blank += "-";
            }
            event.sendResponse("Guess a lowercase letter using e.g. !guess e.");
        }
        else if (messageContent.startsWith(COMM2)&& play) {
            String guess = messageContent.replaceAll(" ", "").replace(COMM2, "");
            if (guess.length() == 1 && guess.compareTo("a") >= 0 && guess.compareTo("z") <= 0) {
                if (mystery.contains(guess)&& !guessed.contains(guess)) {
                    for (int j = 0; j<mystery.length()-1; j++) {
                      if(mystery.substring(j,j+1).equals(guess)) {
                          String before = blank.substring(0, j);
                          String after = blank.substring(j + 1);
                          blank = before + guess + after;
                      }
                    }
                    guessed+=guess;
                    event.sendResponse("Correct! " + blank);
                } else if(guessed.contains(guess)) {
                    event.sendResponse("You have already guessed this letter");
                }else{
                    if(lives>0) {
                        if(lives==1) {
                            guessed+=guess;
                            event.sendResponse("Incorrect! You have 1 guess left!");
                            lives--;
                        }else {
                            guessed+=guess;
                            lives--;
                            event.sendResponse("Incorrect! You have " + lives + " guesses left!");

                        }
                    }else{
                        event.sendResponse("Incorrect! Game over! The word was "+mystery+ "!");
                        play = false;
                    }
                }
            } else {
                event.sendResponse("Please format your guess correctly.");
            }

            if (mystery.equals(blank)) {
                play = false;
                event.sendResponse("Congratulations! You are a winner!!!!!");
            }
        }


        }
    }



