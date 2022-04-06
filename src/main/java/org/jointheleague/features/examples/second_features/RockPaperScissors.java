package org.jointheleague.features.examples.second_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import ch.qos.logback.core.joran.conditional.IfAction;

public class RockPaperScissors extends Feature {

	public final String COMMAND = "!rps";
	private final Random random = new Random();

	public RockPaperScissors(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Your classic rock paper scissors game");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		int r = random.nextInt(3);

		if (messageContent.startsWith(COMMAND)) {

			event.getChannel().sendMessage("3");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().sendMessage("2");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().sendMessage("1");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().sendMessage("Shoot!");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (r == 0) {
				event.getChannel().sendMessage("Rock");
			} else if (r == 1) {
				event.getChannel().sendMessage("Paper");
			} else {
				event.getChannel().sendMessage("Scissors");
			}

			if (messageContent.contains("rock")) {

			}
			else if (messageContent.contains("scissors")||messageContent.contains("scissor")) {

			}
			else if (messageContent.contains("paper")) {

			}
			else {
				
			}
			event.getChannel().sendMessage("");

		}
	}

}
