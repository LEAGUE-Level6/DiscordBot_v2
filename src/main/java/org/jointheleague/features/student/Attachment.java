package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;


public class Attachment extends Feature{
    public final String ATTACH4 = "!attach4";
    boolean gameStarted = false;
    boolean roundStarted = false;

    char[][] field = new char[][] {{'O', 'O', 'O', 'O', 'O'},{'O', 'O', 'O', 'O', 'O'}
            ,{'O', 'O', 'O', 'O', 'O'},{'O', 'O', 'O', 'O', 'O'},{'O', 'O', 'O', 'O', 'O'}};

    public Attachment(String channelName) {
        super(channelName);

        helpEmbed = new HelpEmbed(
                ATTACH4,
                "type !attach4 to start the game, then pick through columns 1-6 on each turn " +
                        "to place pieces"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        String rowToPrint = "";

        if (messageContent.startsWith(ATTACH4)) {
            event.getChannel().sendMessage("Welcome to Attach. It's really Connect4 but with a different name. " +
                    "type a !column and the number of the column you would like to place your piece. ex: !column2 for column 2." +
                    "Good luck.");
            gameStarted = true;
            roundStarted = true;
        }

        if (gameStarted == true) {
            if (roundStarted) {
                for (int row = 0; row < field.length; row++) {
                    for (int column = 0; column < field[row].length; column++) {
                        rowToPrint += field[row][column];
                    }
                    event.getChannel().sendMessage(rowToPrint);
                    rowToPrint = "";
                }
                roundStarted = false;
            }

            if (messageContent.startsWith("!column1")) {
                switchValue(0);
            } else if (!messageContent.contains("!column2")) {
                switchValue(1);
            } else if (!messageContent.contains("!column3")) {
                switchValue(2);
            } else if (!messageContent.contains("!column4")) {
                switchValue(3);
            } else if (!messageContent.contains("!column5")) {
                switchValue(4);
            }
        }
    }
    public void switchValue(int column) {
        for (int row = field.length; row > 0; row++) {
            if (field[row][0] == 'O') {
                field[row][0] = 'X';
                break;
            }
        }
    }
}
