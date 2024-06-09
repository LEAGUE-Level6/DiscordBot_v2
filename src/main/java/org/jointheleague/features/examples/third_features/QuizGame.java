package org.jointheleague.features.examples.third_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class QuizGame extends Feature {

    public final String COMMAND = "q!question";

    public static int progress;

    public static BufferedReader br;

    private static String question;
    private static String answer;
    private static ArrayList<String> questions;
    Random random;

    private static int score;
    private static int r;

    private static int numqtn = 10;

    public QuizGame(String channelName) {
        super(channelName);

        progress = -1;
        //Create a help embed to describe feature when !help command is sent
        question = "Error: Question not found";
        score = 0;
        r = -1;
        random = new Random();
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A 10-question quiz game, in which the bot pulls randomized questions from an attached file to ask the user, whose responses take the form 'q!answer [answer]'. " +
                        "Spelling matters, but capitalization does not."
        );
    }

    private void startGame(MessageCreateEvent event) {
        progress = 1;
        score = 0;
        questions = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("src/main/java/org/jointheleague/features/examples/third_features/questions.txt"));
            String line = br.readLine();
                while(line != null){
                    questions.add(line);
                    line = br.readLine();
                }
                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.getChannel().sendMessage("A game has been started");
        event.getChannel().sendMessage(getQuestion(event));
    }

    private void endGame(MessageCreateEvent event) {
        event.getChannel().sendMessage("You answered " + score + " out of " + numqtn + " questions correctly.");
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progress = 0;
        r = -1;
    }

    private String getQuestion(MessageCreateEvent event) {
        int r = random.nextInt(questions.size());
        question = questions.get(r);
        questions.remove(r);
            answer = question.substring(question.indexOf('/')+1);
            question = question.substring(0, question.indexOf('/'));
        return question;
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase(Locale.ROOT);
        //event.getChannel().sendMessage("Question command received");  just spammed the channel
        if (messageContent.startsWith(COMMAND)) {
            startGame(event);
        } else if (messageContent.startsWith("q!answer")) {
            if (progress>0 && progress<=numqtn) {
                messageContent = messageContent.substring(messageContent.indexOf(' ') + 1);
                if (messageContent.equals(answer.toLowerCase(Locale.ROOT))) {
                    score++;
                    event.getChannel().sendMessage("Correct!");
                } else {
                    event.getChannel().sendMessage("Incorrect! The correct answer was: " + answer);
                }
                progress++;
                event.getChannel().sendMessage(getQuestion(event));
            }
        }
        if (progress > numqtn) {
            endGame(event);
        }
    }

}
