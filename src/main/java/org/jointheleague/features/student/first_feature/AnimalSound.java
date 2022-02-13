package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class AnimalSound extends Feature {
    //command name
    public final String COMMAND = "!animalsound";

    public AnimalSound(String channelName) {
        super(channelName);
        //what shows with !help
        helpEmbed = new HelpEmbed(COMMAND, "Forget that sound an animal makes? Use " + COMMAND + " plus an animal to see what sound it makes! eg. " + COMMAND + " cat");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        //get message contents
        String messageContent = event.getMessageContent().toLowerCase();

        //if message has command
        if (messageContent.contains(COMMAND) && !messageContent.contains("e.g.")) {

            if (messageContent.contains("cat")) {
                //cat
                event.getChannel().sendMessage("Meow! :cat:");
            } else if (messageContent.contains("dog")) {
                //dog
                event.getChannel().sendMessage("Woof! :dog:");
            } else if (messageContent.contains("pig")) {
                //pig
                event.getChannel().sendMessage("Oink! :pig:");
            } else if (messageContent.contains("cow")) {
                //cow
                event.getChannel().sendMessage("Moo! :cow:");
            } else if (messageContent.contains("bird")) {
                //bird
                event.getChannel().sendMessage("Chirp! :baby_chick:");
            } else if (messageContent.contains("frog")) {
                //frog
                event.getChannel().sendMessage("Ribbit! :frog:");
            } else if (messageContent.contains("fox")) {
                //fox hehe
                event.getChannel().sendMessage("Ring-ding-ding-ding-dingeringeding!! :fox:");
            } else if (messageContent.equals(COMMAND)) {
                //no animal
                event.getChannel().sendMessage("Make sure to put an animal's name!");
            } else {
                //bad spelling or other animal
                event.getChannel().sendMessage("Sorry, that animal isn't available at the moment. Try another!");
            }

        }
    }
}
