package org.jointheleague.features.examples.second_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import ch.qos.logback.core.joran.conditional.IfAction;

public class RockPaperScissors extends Feature {

	public final String COMMAND = "!rps";
	private final Random random = new Random();
	boolean shootRPS = false;
	int r = 0;
	int compScore=0;
	int uScore=0;
	String messageContent;

	public RockPaperScissors(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Your classic rock paper scissors game");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		messageContent = event.getMessageContent();
		r = random.nextInt(3);

		 if(shootRPS){
			
			
			if (r == 0) {
				event.getChannel().sendMessage("Rock");
			} else if (r == 1) {
				event.getChannel().sendMessage("Paper");
			} else {
				event.getChannel().sendMessage("Scissors");
			}

			

			messageContent = event.getMessageContent();

			if (messageContent.startsWith("rock")&&r==0) {
				event.getChannel().sendMessage("Tie. Good try, to play again type !rps.");
				compScore++;
				uScore++;
			}
			else if (messageContent.startsWith("rock")&&r==1) {
				event.getChannel().sendMessage("Ha, you lost, I won. Good try, to play again type !rps.");
				compScore++;
			}
			else if (messageContent.startsWith("rock")&&r==2) {
				event.getChannel().sendMessage("Dang, you win. Good job! To play again type !rps");
				uScore++;
			}
			
			else if (messageContent.contains("scissors")&&r==0) {
				event.getChannel().sendMessage("Ha, you lost, I won. Good try, to play again type !rps.");
				compScore++;
			}
			else if (messageContent.contains("scissors")&&r==1) {
				event.getChannel().sendMessage("Dang, you win. Good job! To play again type !rps.");
				uScore++;
			}
			else if (messageContent.contains("scissors")&&r==2) {
				event.getChannel().sendMessage("Tie. Good try, to play again type !rps.");
				compScore++;
				uScore++;
			}
			
			
			else if (messageContent.contains("paper")&&r==0) {
				event.getChannel().sendMessage("Dang, you win. Good job! To play again type !rps");
				uScore++;
			}
			else if (messageContent.contains("paper")&&r==1) {
				event.getChannel().sendMessage("Tie. Good try, to play again type !rps.");
				compScore++;
				uScore++;
			}
			else if (messageContent.contains("paper")&&r==2) {
				event.getChannel().sendMessage("Ha, you lost, I won. Good try, to play again type !rps.");
				compScore++;
			}
			
			
			
			else {
				event.getChannel().sendMessage("To play the game, you can only choose rock, paper, or scissors.");
			}
			
			event.getChannel().sendMessage("Computer Score: "+compScore);
			event.getChannel().sendMessage("Your Score: "+uScore);

			shootRPS = false;
			return;
		}

		else if (messageContent.startsWith(COMMAND) && shootRPS == false) {

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			shootRPS = true;
			return;
		}

	}

}
