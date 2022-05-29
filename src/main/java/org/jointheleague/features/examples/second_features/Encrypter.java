package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import java.util.Locale;

public class Encrypter extends Feature {

    public final String COMMAND = "q!encrypt";

    public Encrypter(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Encrypts the given message with a shift cypher decided by the [shiftSequence] portion of the input. " +
                        "The [shiftSequence] contains a sequence of letters to which 'a' should be shifted (with the rest of the alphabet behaving similarly)," +
                        "with each character in [shiftSequence] taking effect for one character in the message, except for the last, which extends to any " +
                        "remaining message. The output will lack spaces, punctuation, and be entirely capitalized, as encrypted messages have traditionally been. " +
                        "Format: q!encrypt [shiftSequence] [message]"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase(Locale.ROOT);
        if (messageContent.startsWith(COMMAND)) {
            //Something isn't working, no input is returned
            String cipher = messageContent.substring(messageContent.indexOf(' ')+1, messageContent.substring(messageContent.indexOf(' ')).indexOf(' ')).toUpperCase();
            messageContent = messageContent.substring(messageContent.indexOf(' ')+1);
            String msg = messageContent.substring(messageContent.indexOf(' ')).toUpperCase();
            String message = "";
            for (int i = 0; i < msg.length(); i++) {
                if (Character.isLetter(msg.charAt(i))) {
                    if (i < cipher.length()) {
                        message += encrypt(cipher.charAt(i), msg.charAt(i));
                    } else {
                        message += encrypt(cipher.charAt(cipher.length() - 1), msg.charAt(i));
                    }
                }
            }
            event.getChannel().sendMessage(message);
        }
    }

    public char encrypt(char shift, char message) {
        message += (char)(shift-'A');
        if(message > 'Z') message -= 26;
        return message;
    }

}
