package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.data_transfer_objects.Clue;

import javax.swing.*;

public class Feature3 extends Feature {
    JeopardyApi jeopardyApi = new JeopardyApi();

    public final String COMMAND = "!jeopardy";
    int stage = 0;
    int score = 0;
    String answer = "jfioeahjwpifoewh";
    String question = "ifaujwiope";
    int fakeLoop = 100;
    public Feature3(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "The human plays jeopardy by using commands"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {

        String messageContent = event.getMessageContent();

        if (messageContent.startsWith(COMMAND) || messageContent.startsWith("!answer")) {
            if (stage == 0) {
                //respond to message here
                event.getChannel().sendMessage("I will ask you questions and you have to answer them for points.");


                Clue clue = jeopardyApi.getClue(fakeLoop);
                answer = clue.getAnswer();
                question = clue.getQuestion();
                String title = clue.getCategory().getTitle();
                event.getChannel().sendMessage("And your question is " + " " + question);
                stage = 1;
            }
        }
        if (stage == 1) {
            if (event.getMessageContent().contains("!answer")) {

                String recieved = event.getMessageContent();


                if (recieved.contains(answer)) {
                    score += fakeLoop;
                }
                fakeLoop = fakeLoop + 100;
                if(fakeLoop == 1000) {
                    stage = 2;
                }else if (fakeLoop == 700 || fakeLoop == 900) {
                    event.getChannel().sendMessage("(!answer)   no question for this, type into the chanel the following points : " + fakeLoop);
                } else {
                    Clue clue = jeopardyApi.getClue(fakeLoop);
                    answer = clue.getAnswer();
                    question = clue.getQuestion();
                    String title = clue.getCategory().getTitle();
                    event.getChannel().sendMessage("And your question is " + " " + question);

                }
            }
            if (stage == 2) {
                event.getChannel().sendMessage("You got " + score + " points. Game Over");
            }
        }
      }

    }


