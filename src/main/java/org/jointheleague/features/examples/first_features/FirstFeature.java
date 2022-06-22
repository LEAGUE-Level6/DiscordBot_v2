package org.jointheleague.features.examples.first_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;


public class FirstFeature extends Feature {
	
public final String COMMAND = "!licensePlate";

public FirstFeature(String channelName) {
    super(channelName);
    helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random license plate number!");
}

@Override
public void handle(MessageCreateEvent event) {
    String messageContent = event.getMessageContent();
    if (messageContent.startsWith(COMMAND)) {
    	Random r = new Random();  
    	int num1= r.nextInt(9);
    	Random r2 = new Random();
    	char let2=(char)(r2.nextInt(26) + 'a');
    	Random r3 = new Random();
    	char let3=(char)(r3.nextInt(26) + 'a');
    	Random r4 = new Random();
    	char let4=(char)(r4.nextInt(26) + 'a');
    	Random r5 = new Random();
    	int num5= r5.nextInt(9);
    	Random r6 = new Random();
    	int num6= r6.nextInt(9);
    	Random r7 = new Random();
    	int num7= r7.nextInt(9);
    	event.getChannel().sendMessage("Your random license plate is " + num1 + Character.toUpperCase(let2)+Character.toUpperCase(let3)+Character.toUpperCase(let4)+num5+num6+num7);
    }
}
}


