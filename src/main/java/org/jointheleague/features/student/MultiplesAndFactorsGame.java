package org.jointheleague.features.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class MultiplesAndFactorsGame extends Feature implements ActionListener {

	public final String COMMAND = "!MaFGame";

	boolean gameStarted = false;
	int round = 0;
	int score = 0;

	int multiple;
	int factor;

	Timer timer;
	Random rand = new Random();
	TextChannel textChannel;

	public MultiplesAndFactorsGame(String channelName) {
		super(channelName);

		helpEmbed = new HelpEmbed(COMMAND,
				"This is a game with 5 rounds. Each round, you will be asked to enter a number into a channel."
						+ "The number has to be a multiple of a specified number and has to have a specifed factor."
						+ "At the end of the 5 rounds, a score will be printed."
						+ "The game can be stopped by typing the \"!stop\" command. The game will also automatically time out after 30 seconds with no response.");
	}

	String generateQuestion() {
		multiple = rand.nextInt(20) + 1;
		factor = rand.nextInt(20) + 1;

		return "What is a number that is a multiple of " + multiple + " and has a factor of " + factor + "?";
	}

	void startGame() {
		gameStarted = true;
		timer = new Timer(30000, this);

		textChannel.sendMessage("Round 1: " + generateQuestion());
		round++;
		timer.start();
	}

	void roundIteration() {
		round++;

		if (round > 5) {
			textChannel.sendMessage("Game Over! Your score is: " + score + "/5");
			resetGameVariables();
		} else {
			textChannel.sendMessage("Round " + round + ": " + generateQuestion());
			timer.restart();
		}
	}

	boolean checkAnswer(String messageContent) throws IllegalArgumentException {
		int response = Integer.parseInt(messageContent);

		if (response % multiple != 0 || response % factor != 0) {
			textChannel.sendMessage("Your answer is incorrect. Suggested Answer: " + (multiple * factor));
			return false;
		} else {
			score++;
			textChannel.sendMessage("Your answer is correct!");
			return true;
		}
	}

	void stopGame() {
		textChannel.sendMessage("Game Stopped");
		resetGameVariables();
	}

	void resetGameVariables() {
		timer.stop();

		gameStarted = false;
		round = 0;
		score = 0;

		timer = null;
		textChannel = null;
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		if (!event.getMessageAuthor().isBotUser()) {
			String messageContent = event.getMessageContent();

			if (messageContent.contains(COMMAND) && !gameStarted) {
				textChannel = event.getChannel();
				startGame();

			} else if (gameStarted && round <= 5) {
				if (messageContent.equals("!stop")) {
					stopGame();

				} else {
					try {
						checkAnswer(messageContent);
						roundIteration();
					} catch (IllegalArgumentException e) {
						textChannel.sendMessage("That's not a valid number! Try again.");
					}
					
				}
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		textChannel.sendMessage("Game Timed Out: No response for 30 seconds.");
		resetGameVariables();
	}

	// Gets and Sets for Testing

	boolean getGameStarted() {
		return gameStarted;
	}

	int getRound() {
		return round;
	}

	int getScore() {
		return score;
	}

	int getMultiple() {
		return multiple;
	}

	int getFactor() {
		return factor;
	}

	Timer getTimer() {
		return timer;
	}

	TextChannel getTextChannel() {
		return textChannel;
	}

	void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	void setRound(int round) {
		this.round = round;
	}
	
	void setScore(int score) {
		this.score = score;
	}

	void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	void setFactor(int factor) {
		this.factor = factor;
	}

	void setTimer(Timer timer) {
		this.timer = timer;
	}

	void setRandom(Random rand) {
		this.rand = rand;
	}

	void setTextChannel(TextChannel textChannel) {
		this.textChannel = textChannel;
	}

}
