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
                "Literally just hangman"
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            if (messageContent.equals(COMMAND)){
                wordToGuess = Utilities.readRandomLineFromFile("src/main/java/org/jointheleague/features/student/first_feature/dictionary.txt");
                for (int i = 0; i < wordToGuess.length(); i++){
                    currentDisplay += "_";
                }
                event.sendResponse("There is a word you need to guess, guess a letter by doing the command, then a letter. Ex: \"!chillGame e\"" + "\nDEBUG: " + wordToGuess);
                System.out.println(wordToGuess);
            } else {
                //check if the game has been started
                if(wordToGuess.isEmpty()){
                    //tell them to start the game first
                    event.sendResponse("Please start the game first using just the command");
                    return;
                }

                //parse the guess from the message
                String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");

                //change the guess to a char
               if (!guessMessage.equalsIgnoreCase(wordToGuess)){
                   char guess = 0;
                   try{
                       guess = guessMessage.charAt(0);
                   }
                   catch(IndexOutOfBoundsException e){
                       //tell them to format their guess properly
                       event.sendResponse("Please format your guess like this: " + COMMAND + " e");
                       return;
                   }

                   if (wordToGuess.contains(guess + "") && currentDisplay.contains(guess + "")){
                       System.out.println("skib");
                    char[] chars = currentDisplay.toCharArray();
                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.charAt(i) == guess){
                            chars[i] = guess;
                        }
                    }
                    currentDisplay = new String(chars);
                   } else {
                       event.sendResponse("Incorrect Guess!!\n" + currentDisplay);
                   }

               } else {
                    //they got it correct
                    event.sendResponse("Correct!  The word I picked was " + wordToGuess);
                    //set wordToGuess back to empty string
                    wordToGuess = "";
               }


            }
        }
    }

}
