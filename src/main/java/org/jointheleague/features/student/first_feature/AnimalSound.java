package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class AnimalSound extends Feature {
    public final String COMMAND = "!animalsound";

    public AnimalSound(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Forget that sound an animal makes? Use " + COMMAND + " plus an animal to see what sound it makes! eg. " + COMMAND + " cat");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent().toLowerCase();

        if (messageContent.contains(COMMAND)  && !messageContent.contains("e.g.")){


            if(messageContent.contains("cat")){
                event.getChannel().sendMessage("Meow! :cat:");
            }
            else if (messageContent.contains("dog")) {
                event.getChannel().sendMessage("Woof! :dog:");
            }
            else if (messageContent.contains("pig")) {
                event.getChannel().sendMessage("Oink! :pig:");
            }
            else if (messageContent.contains("cow")) {
                event.getChannel().sendMessage("Moo! :cow:");
            }
            else if (messageContent.contains("bird")) {
                event.getChannel().sendMessage("Chirp! :baby_chick:");
            }
            else if (messageContent.contains("frog")) {
                event.getChannel().sendMessage("Ribbit! :frog:");
            }
            else if (messageContent.contains("fox")) {
                event.getChannel().sendMessage("Ring-ding-ding-ding-dingeringeding!! :fox:");
            }
            else {
                event.getChannel().sendMessage("Sorry, that animal isn't available at the moment. Try another!");
            }

        }
    }
}
