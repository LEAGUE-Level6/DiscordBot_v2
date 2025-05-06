package org.jointheleague.features.student.second_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!unscramble";

    public String[][] words = {
            {"six", "cat", "dog", "two"},//l1
            {"hi", "bye"},
            {"hi", "bye"},
            {"hi", "bye"},
            {"hi", "bye"},
            {"hi", "bye"},
            {"hi", "bye"},
            {"hi", "bye"},
    };

    public FeatureTwo(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Starts a game of unscramble. You are given a series of scrambled letters, and have to guess the unscrambled word. " +
                        "This game starts easy and gets harder. Points are tracked."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        System.out.println(messageContent);
        if (messageContent.startsWith(COMMAND)) {
            System.out.println("cmd");
            String[] responseScramble = getScrambled(0);
            //respond to message here
            System.out.println("recieved");
            event.sendResponse(responseScramble[0] + responseScramble[1]);
        }
    }

    public String[] getScrambled(int level) {
        System.out.println("scramble");
        String[] scrambled = new String[2];
        Random rand = new Random();
        String word = words[level][rand.nextInt(words[level].length - 1)];
        char[] arrayofChars = word.toCharArray();
        for (int i = 0; i < word.length() - 1; i++) {
            Character c = word.charAt(i);
            Random r = new Random();
            int ran = rand.nextInt(word.length() - 1);

            arrayofChars[i] = arrayofChars[ran];
            arrayofChars[ran] = c;

        }
        scrambled[0] = new String(arrayofChars);
        scrambled[1] = word;
        return scrambled;
    }

}