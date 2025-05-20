package org.jointheleague.features.student.second_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!unscramble";

    boolean ready = false;
    int levelCounter=0;
    String[] responseScramble = null;
    public String[][] words = {
            {"six", "cat", "dog", "two","bye","man","fan","pan","zap", "hat","log","rug","pug","wet","net", "set","fix","and"},//l1
            {"iron","wood","tree","tent","duck","king","high","gold","game","fire","hate","inch","deck","farm","dust","flow","food","halt"},//l2
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
                        "This game starts easy and gets harder. Points are tracked."+"\n Reply/Answer using the command "+COMMAND
        );
    }

    @Override
    public void handle(ReceivedMessage event) {

        String messageContent = event.getMessageContent();
        System.out.println(messageContent);
        if (messageContent.startsWith(COMMAND)&&!ready) {

             responseScramble = getScrambled(levelCounter);
            if(levelCounter==0){
                event.sendResponse("Alright, lets start a game of unscramble! \n" +
                        "Unscramble the letters and reply with: \n"+COMMAND+" followed by your guess.\nLevel "+(levelCounter+1)+": "+responseScramble[0] );
            }
            else {
                event.sendResponse("Level " + (levelCounter + 1) + ": " + responseScramble[0]);
            }
            ready = true;
        }
        if (messageContent.startsWith(COMMAND)&&ready){
            if(responseScramble[1].equals(messageContent.substring(COMMAND.length()+1))){
                event.sendResponse("Nice, You unscrambled it correctly!\nThe word was "+responseScramble[1]+"\n Type "+COMMAND+" to continue!");
                ready=false;
                levelCounter+=1;
            }
            else{
                event.sendResponse("Not quite! Try again.");
            }

        }
    }

    public String[] getScrambled(int level) {
        System.out.println("scramble");
        String[] scrambled = new String[2];
        Random rand = new Random();
        String word = words[level][rand.nextInt(words[level].length - 1)];
        char[] arrayofChars = word.toCharArray();
        for (int i = 0; i < word.length() ; i++) {
            char c = arrayofChars[i];
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