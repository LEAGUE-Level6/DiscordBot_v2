package org.jointheleague.features.student.feature1.sameer;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Prime extends Feature {

    public final String COMMAND = "!prime";

    public Prime(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            try {
                String sub = messageContent.substring(7);
                int number = Integer.parseInt(sub);
                event.getChannel().sendMessage(isPrime(number));
            } catch(StringIndexOutOfBoundsException e){
                event.getChannel().sendMessage("You didn't provide a number");
            } catch (NumberFormatException e) {
                event.getChannel().sendMessage("You wrote an invalid number");
            }
        }
    }

    public String isPrime(int num) {
        if (num == 1) return "1 is neither prime nor composite";
        if (num == 2) return "2 is prime";
        for (int i = 2; i <= Math.ceil(Math.sqrt(num)); i++) {
            if (num % i == 0)
                return num + " isn't prime because it is divisible by " + i;
        }
        return num + " is prime";
    }

}
