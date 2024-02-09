package org.jointheleague.features.student.second_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.ArrayList;
import java.util.Random;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!quiz";
    int gameStart = 0;
    int countInt = 0;
    ArrayList<Question> questions = new ArrayList<>();
    public FeatureTwo(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This command allows you to answer an assortment of quiz questions."
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("Welcome! This is a fast facts quiz function. Type !quizStart to begin ");
            gameStart = 1;
        }
        else if(messageContent.startsWith(COMMAND) || messageContent.contains("CORRECT!") || messageContent.contains("SKIPPED!")){
            if(gameStart == 0) {
                event.getChannel().sendMessage("You need to start the game first by using '!quiz'");
                return;
            //Create question variable to hold the current question being held.
                // Do all internalising of checking if answer is correct or any othher things like that
                //Randomise questions selected and other stuff
            }
        }
    }
    public void createQuestions() {
        questions.add(new Question("``` What is 2 + 2. Answer like '!quizQ1:5' ```","4"));
        questions.add(new Question("``` What is the capital of California Answer like '!quizQ2:San Diego' ```", "Sacramento"));
        questions.add(new Question("``` Who is our current president '!quizQ3:Obama' ```","Biden"));

    }
}
