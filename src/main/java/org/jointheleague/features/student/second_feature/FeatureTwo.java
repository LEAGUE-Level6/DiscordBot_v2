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
    Question setBlank = new Question("Content","Answer");
    Question currentQ = new Question("Content","Answer");
    int rando=0;
    int qCount = 1;
    public FeatureTwo(String channelName) {
        super(channelName);
        createQuestions();
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
            event.getChannel().sendMessage("Welcome! This is a fast facts quiz function. Question 1 will begin shortly...");
            gameStart = 1;
            getRandQuestion();
            event.getChannel().sendMessage("Question " + qCount + ": " + currentQ.getContent());

        }
        else if(!messageContent.contains(COMMAND) && !messageContent.contains("Question") && !messageContent.contains("correct")){
            if(gameStart == 0) {
                return;
            }
            if(currentQ.checkAnswer(messageContent)) {
                qCount++;
                event.getChannel().sendMessage("CORRECT!!! Question " + qCount + " will begin shortly...");
                if(questions.size()==0) {
                    event.getChannel().sendMessage("Sorry but we're actually out of new questions for you! Good job getting so many correct!");
                }
                currentQ=setBlank;
                getRandQuestion();
                event.getChannel().sendMessage("Question " + qCount + ": " + currentQ.getContent());
            } else {
                event.getChannel().sendMessage("Incorrect! Try again");
            }


        }
    }
    public void createQuestions() {
        questions.add(new Question("``` What is 2 + 2?```","4"));
        questions.add(new Question("``` What is the capital of California?```", "Sacramento"));
        questions.add(new Question("``` Who is our current president?```","Joe Biden"));
        questions.add(new Question("``` Who was our first president?```","George Washington"));
        questions.add(new Question("``` What are the first 3 digits of pi?```","3.14"));
        questions.add(new Question("``` What is 1 * 3080450?```","3080450"));
        questions.add(new Question("``` What popular instrument has black and white keys?```","Piano"));
        questions.add(new Question("``` Who performed the half time show at this years Super Bowl?```","Usher"));
        questions.add(new Question("``` What state was the gold rush predominantly in?```","California"));
        questions.add(new Question("``` Who is the lead singer of the band Queen?```","Freddie Mercury"));
    }

    public void getRandQuestion() {
        Random rand = new Random();
        rando = rand.nextInt(questions.size());
        currentQ = questions.get(rando);
        questions.remove(rando);
    }
}
