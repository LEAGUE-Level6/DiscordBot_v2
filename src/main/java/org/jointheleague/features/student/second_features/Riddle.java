package org.jointheleague.features.student.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.HashMap;
import java.util.Random;

public class Riddle extends Feature {

    public final String COMMAND = "!riddle";
    private final Random random = new Random();
    private final String answer = "egg";
    String[] answers = { "egg", "candle", "all of them" };
    String[] questions = { "What has to be broken before you can use it?", " I’m tall when I’m young, and I’m short when I’m old. What am I?", "What month of the year has 28 days?" };
    String COMMAND2;
    int ran;
    public void randomCreater(){
        ran = random.nextInt(3);
        COMMAND2 = "!riddle " + answers[ran];
    }

    public Riddle(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Riddle game. You will be given a riddle, your goal is to get the answer.");
    }

    @Override
    public void handle(MessageCreateEvent event){
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equalsIgnoreCase((COMMAND))){
            randomCreater();
            event.getChannel().sendMessage(questions[ran]);
        }
        //check a guess
        if (messageContent.equalsIgnoreCase(COMMAND2)) {
            event.getChannel().sendMessage("Correct! The answer was " + answers[ran] + ".");

        }
        }
    }
