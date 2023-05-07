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
            if (messageContent.indexOf(' ') == -1) {
                event.getChannel().sendMessage("Please include both a [shiftSequence] and a [message].");
                return;
            }
            messageContent = messageContent.substring(messageContent.indexOf(' ') + 1);
            if (messageContent.equals("") ||messageContent.indexOf(' ') == -1|| messageContent.substring(messageContent.indexOf(' ')).length() <= 1) {
                event.getChannel().sendMessage("Please include both a [shiftSequence] and a [message].");
                return;
            }
            String cipher = messageContent.substring(0, messageContent.indexOf(' ')).toUpperCase();
            String msg = messageContent.substring(messageContent.indexOf(' ') + 1).toUpperCase();
            event.getChannel().sendMessage(encrypt(cipher, msg));
        }
    }

    public String encrypt(String shift, String message) {
        String op = "";
        char m;
        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                if (i < shift.length()) {
                    m = (char)(message.charAt(i)-'A'+shift.charAt(i));
                    if(m > 'Z') m -= 26;
                    op += m;
                } else {
                    m = (char)(message.charAt(i)-'A'+shift.charAt(shift.length()-1));
                    if(m > 'Z') m -= 26;
                    op += m;
                }
            }
        }
        return op;
    }

}
