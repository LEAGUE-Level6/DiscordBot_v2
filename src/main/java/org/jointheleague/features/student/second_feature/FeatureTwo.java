package org.jointheleague.features.student.second_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Random;

public class FeatureTwo extends Feature {

    public final String COMMAND = "!unscramble";
    Random rand = new Random();
    boolean ready = false;
    int levelCounter=0;
    String[] responseScramble = null;


    public String[][] words = {
            {"six", "cat", "dog", "two","bye","man","fan","pan","zap", "hat","log","rug","pug","wet","net", "set","fix","and"},//l1
            {"iron","wood","tree","tent","duck","king","high","gold","game","fire","hate","inch","deck","farm","dust","flow","food","halt"},//l2
            {"eager", "eagle","cable", "cabin", "caddy", "apple","noise", "sound","wheel","hills","boxes","threw","chart","share","worth","chord","labor"},//l3
            {"honest", "drawer","adieu","holder","govern","liquid","lonely","evolve", "excess","doctor","singer","easily","driven","fourth","friend","casino","canyon","combat"},//l4
            {"abandon", "advisor","allured","ironed","anxious","babysat","backlit","advance","bargain","boombox","gallery","gallons","habitat","haircut","eagerly","soundly","eardrum","factory"},//l5
            {"capacity", "engineer","aircraft","advanced","delivery","compound","exposure","original","northern","galactic","reaction","triangle","treasury","humanity","included","facility","everyday","equation"},//l6
            {"beginning", "household","candidate","jewellery","deduction","important","marketing","governing","practical","judgement","breathing","combating","zealously","reconquer","normalize","objectify","quadratic"},//l7
            {"puzzlingly", "highjacked","maximizing","texturized","laboringly","racetracks","nationhood","complexity","aquaphobia"},//l8
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

        if (messageContent.startsWith(COMMAND)&&!ready) {
            int id = rand.nextInt(words[levelCounter].length);
            getScrambled(levelCounter, id);
            responseScramble = getScrambled(levelCounter, id);
            if (levelCounter == 0) {
                event.sendResponse("Alright, lets start a game of unscramble! \n" +
                        "Unscramble the letters and reply with: \n" + COMMAND + " followed by your guess.\nLevel " + (levelCounter + 1) + ": " + responseScramble[0]);
            } else {
                event.sendResponse("Level " + (levelCounter + 1) + ": " + responseScramble[0]);
            }
            ready = true;
        }
        if (messageContent.startsWith(COMMAND)&&ready) {
            if (messageContent.length() > COMMAND.length()) {
                if (responseScramble[1].equals(messageContent.substring(COMMAND.length() + 1))) {
                    if (levelCounter != 7) {
                        event.sendResponse("Nice, You unscrambled it correctly!\nThe word was " + responseScramble[1] + "\nType " + COMMAND + " to continue!");
                        ready = false;
                        levelCounter += 1;
                    } else {
                        event.sendResponse("WOW, You've beaten  the game! Congrats on unscrambling all those words! \nType !help to find more commands!");
                        ready = false;
                        levelCounter = 0;
                    }
                } else {
                    event.sendResponse("Not quite! Try again.");
                }

            }
        }
    }

    public String[] getScrambled(int level, int num) {

        String[] scrambled = new String[2];
        String word = words[level][num];
        char[] arrayofChars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char c = arrayofChars[i];

            int ran = rand.nextInt(word.length());

            arrayofChars[i] = arrayofChars[ran];
            arrayofChars[ran] = c;

        }
        scrambled[0] = new String(arrayofChars);
        scrambled[1] = word;
        return scrambled;
    }

}
