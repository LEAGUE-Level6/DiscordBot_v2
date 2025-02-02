package org.jointheleague.features.student.first_feature;

import java.util.Random;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class GuessTheSong extends Feature {

    public final String COMMAND = "!guessthesong";
    private String currentSong;
    private String currentLyrics;
    private final String[][] songs = {
            {"Don't Stop Believin'", "Just a small town girl, living in a lonely world..."},
            {"Bohemian Rhapsody", "Is this the real life? Is this just fantasy?"},
            {"Shape of You", "The club isn't the best place to find a lover, so the bar is where I go..."},
            {"Blinding Lights", "I said, ooh, I'm blinded by the lights..."}
    };
    private boolean isGameActive = false;
    
    public GuessTheSong(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This game will give you a lyric, and you will have to guess the song it's from. "
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            if (isGameActive) {
            	event.sendResponse("Game is in progress.");
            	
            }
            
            Random random = new Random();
            int index = random.nextInt(songs.length);
            currentSong = songs[index][0];
            currentLyrics = songs[index][1];
            
            event.sendResponse("Guess the Song! Lyrics; " + currentLyrics);
            isGameActive = true;
        }
            else if (isGameActive && messageContent.equalsIgnoreCase(currentSong)) {
            	event.sendResponse("Correct!");
            	isGameActive = false;
            }
            
    }

}

