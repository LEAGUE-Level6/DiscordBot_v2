package org.jointheleague.features.student.second_feature;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WordChainFeature extends FeatureTemplate {
	public final String START_COMMAND = "!startwordchain";
    public final String WORD_COMMAND = "!word";
    public final String END_COMMAND = "!endwordchain";

    private String lastWord = null;
    private ArrayList<String> usedWords = new ArrayList<>();

    public WordChainFeature(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                START_COMMAND + ", " + WORD_COMMAND + ", " + END_COMMAND,
                "Play a word chain game! \n" +
                        "!startwordchain [word] - Start the game with a word.\n" +
                        "!word [word] - Submit a word that starts with the last letter of the previous word.\n" +
                        "!endwordchain - End the game."
        );
    }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        String[] words = messageContent.split(" ", 2);

        String command = words[0];
        String word = words.length > 1 ? words[1].toLowerCase() : "";

        if(command == START_COMMAND) {
            startGame(event, word);
    }
    else if (command == WORD_COMMAND) {
            playWord(event, word);
    }
    else {
            endGame(event);
    }
    }

    private void startGame(ReceivedMessage event, String word) {
        if (word.isEmpty()) {
            event.sendResponse("Please provide a starting word. Example: !startwordchain apple");
            return;
        }

        lastWord = word;
        usedWords.clear();
        usedWords.add(word);
        event.sendResponse("Word chain started with: " + word);
    }

    private void playWord(ReceivedMessage event, String word) {
        if (lastWord == null) {
            event.sendResponse("No active game.");
            return;
        }

        if (word.isEmpty()) {
            event.sendResponse("Please provide a word.");
            return;
        }

        if (usedWords.contains(word)) {
            event.sendResponse("That word has already been used!");
            return;
        }

        if (word.charAt(0) != lastWord.charAt(lastWord.length() - 1)) {
            event.sendResponse("Invalid word! Must start with " + lastWord.charAt(lastWord.length() - 1) );
            return;
        }

        lastWord = word;
        usedWords.add(word);
        event.sendResponse("Good. Next word should start with: " + lastWord.charAt(lastWord.length() - 1));
    }

    private void endGame(ReceivedMessage event) {
        lastWord = null;
        usedWords.clear();
        event.sendResponse("Word chain game has ended.");
    }
}
