package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class Trivia extends Feature {

    public final String COMMAND = "!trivia";
    public boolean gameStarted = false;
    int questionNumber;
    String answer;
    Random ran = new Random();
    public Trivia(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Trivia questions for the user to answer. Start the game with !trivia and enter a guess using !trivia guess. E.g. !trivia b"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

    System.out.println(messageContent);
        if (messageContent.equals(COMMAND)) {
            questionNumber = ran.nextInt(1);
            switch(questionNumber) {
                case 0:
                    event.getChannel().sendMessage("Trivia:What is the main ingredient in guacamole?\na) Tomatoes\nb) Avocados\nc) Lemons\nd) Tortilla chips");
                    answer = "b";
                }
            }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("Trivia:"))
                {

            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");

            if (guessMessage.equalsIgnoreCase(answer)) {
                //tell them it's too low
                event.getChannel().sendMessage(answer + " is correct!");
            }
             else {

                event.getChannel().sendMessage("Wrong, the correct answer is " + answer);
                //set numberToGuess back to 0
                gameStarted = false;
            }

        }
    }

}
