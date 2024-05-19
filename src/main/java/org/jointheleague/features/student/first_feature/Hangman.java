package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Hangman extends Feature {

    public final String COMMAND = "!hangman";
    public final String COMM2 = "!guess";
    public String mystery;
    public String blank;
    public boolean solved = false;

    public Hangman(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Play a game of hangman with the computer. Can you best a machine in this world renowned battle of the wits?"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            //respond to message here
            String[] words = {"memento", "executive", "abysmal", "oxygen", "lucid", "brochure", "candid", "diplomat", "gratuitous", "feral", "holiday", "inclusive", "junta", "kimono", "negligible", "preclude"};
            int rand = (int) (Math.random() * words.length) + 0;
             mystery = words[rand];
             blank = "";
            for (int i = 0; i < mystery.length(); i++) {
                blank += "-";
            }
            event.getChannel().sendMessage(mystery);
            event.getChannel().sendMessage(blank);
            event.getChannel().sendMessage("Guess a lowercase letter using e.g. !guess e.");
        }
        else if (messageContent.startsWith(COMM2)) {
            String guess = messageContent.replaceAll(" ", "").replace(COMM2, "");
            if (guess.length() == 1 && guess.compareTo("`") > 0 && guess.compareTo("{") < 0) {
                if (mystery.contains(guess)) {
                    for (int j = 0; j<mystery.length(); j++) {
                      if(mystery.substring(j,j+1).equals(guess)) {
                          String before = blank.substring(0, j);
                          String after = blank.substring(j + 1);
                          blank = before + guess + after;
                      }
                    }
                    event.getChannel().sendMessage("Correct! " + blank);
                } else {
                    event.getChannel().sendMessage("Incorrect! Guess again!");
                }
            } else {
                event.getChannel().sendMessage("Please format your guess correctly.");
            }

            if (mystery.equals(blank)) {
                solved = true;
                event.getChannel().sendMessage("Congratulations! You are a winner!!!!!");
            }
        }


        }
    }



