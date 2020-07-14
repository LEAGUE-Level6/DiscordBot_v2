package org.jointheleague.features.example;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discordbot.CustomMessageCreateListener;
import org.jointheleague.pojo.help_embed.HelpEmbed;

public class RandomNumber extends CustomMessageCreateListener {

	public final String COMMAND = "!random";
	
	public RandomNumber(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number.  You can also specify a range of values (e.g. !random 50-100)");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		if (messageContent != null && messageContent.contains(COMMAND)) {
			
			String cmd = messageContent.replaceAll(" ", "").replace("!random","");
			
			if(cmd.equals("")) {
				
				Random r = new Random();
				event.getChannel().sendMessage("Your random number is " + r.nextInt(1000));
				
				
			} else {

				String rlow = cmd.substring(0, cmd.indexOf('-'));
				String rhigh = cmd.replace(rlow + '-', "");

				event.getChannel().sendMessage("Your random number between " + rlow + " and " + rhigh + " is " + ThreadLocalRandom.current().nextInt(Integer.parseInt(rlow), Integer.parseInt(rhigh)));

			}
			
		}
	}
	
}
