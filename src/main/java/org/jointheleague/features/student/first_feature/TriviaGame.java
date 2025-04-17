package org.jointheleague.features.student.first_feature;

import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

public class TriviaGame extends FeatureTemplate {
    public final String COMMAND = "!trivia";

    private WebClient webClient;
    public static final String baseUrl = "https://opentdb.com/api.php?amount=10";

    public TriviaGame(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Play a trivia game, it'll be pretty chill."
        );

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }
//IGNORE
    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            if (messageContent.equals(COMMAND)){
                event.sendResponse("I will tell you a trivia question, and you will attempt to answer it using the command. Ex: \"!trivia George Washington\"");
            }
        }
    }
}

// Chill Game here to use bits and pieces for logic.
/*
String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            if (messageContent.equals(COMMAND)){
                if (wordToGuess.isEmpty()){
                    wordToGuess = Utilities.readRandomLineFromFile("src/main/java/org/jointheleague/features/student/first_feature/dictionary.txt");
                    for (int i = 0; i < wordToGuess.length(); i++){
                        currentDisplay += "-";
                    }
                    event.sendResponse("There is a word you need to guess, guess a letter by doing the command, then a letter. Ex: \"!chillGame e\"");
                    System.out.println(wordToGuess);
                } else {
                    event.sendResponse("You still haven't guessed the current word!");
                }
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

                   if (wordToGuess.contains(guess + "") && !currentDisplay.contains(guess + "")){

                    char[] chars = currentDisplay.toCharArray();
                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.charAt(i) == guess){
                            chars[i] = guess;
                        }
                    }
                    currentDisplay = new String(chars);
                    if (currentDisplay.contains("-")){
                        event.sendResponse("Correct Guess!!\n" + currentDisplay);
                    } else {
                        event.sendResponse("Correct! The word I picked was " + wordToGuess);
                        wordToGuess = "";
                        currentDisplay = "";
                    }

                   } else {
                       event.sendResponse("Incorrect Guess!!\n" + currentDisplay);
                   }

               } else {
                    //they got it correct
                    event.sendResponse("Correct! The word I picked was " + wordToGuess);
                    //set wordToGuess back to empty string
                    wordToGuess = "";
                    currentDisplay = "";
               }


            }
        }
    }
 */
