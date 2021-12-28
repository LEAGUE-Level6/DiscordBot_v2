package org.jointheleague.features.student;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class SnakeGame extends Feature implements ActionListener {

	public final String COMMAND = "!snake";
	Message gameMessage;
	Messageable gameChannel;
	User player;
	String[] emojis = {"⬅️", "⬆️", "⬇️", "➡️"};

	String[][] gameBoard = new String[8][8];
	Timer gameTimer;
	boolean gameStarted = false;

	ArrayList<SnakeSegment> segments = new ArrayList<SnakeSegment>();
	SnakeSegment head;
	int direction; // 1:Up , 2:Right , 3:Down , 4:Left

	int foodX;
	int foodY;

	public SnakeGame(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"Play a game of snake! Instructions are provided when a game is started.\n"
				+ "(The game starts immediately, so you might die at first.)");
	}

	public void setup() {
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				gameBoard[j][i] = ":green_square:";
			}
		}

		head = new SnakeSegment(4, 3);
		segments.add(head);
		segments.add(new SnakeSegment(3, 3));
		segments.add(new SnakeSegment(2, 3));
		gameBoard[3][4] = ":blue_square:";
		gameBoard[3][3] = ":blue_square:";
		gameBoard[3][2] = ":blue_square:";
		direction = 2;

		spawnFood();
		
		gameTimer = new Timer(1000, this);
		gameTimer.setInitialDelay(2000);
	}

	public EmbedBuilder createEmbed() {

		EmbedBuilder gameEmbed = new EmbedBuilder().setColor(Color.BLACK).setTitle("Tiny Snake Game!")
				.addField("Help the snake get the oranges!", createGameBoard())
				.addField("How to Play:", "Use the arrow emojis to move the snake around.\n"
						+ "Direct the snake to the orange tiles to grow your snake.\n"
						+ "Don't run into the edges of the map, or the snake itself!\n"
						+ "Trying to make your snake as long as possible!");

		return gameEmbed;
	}

	public String createGameBoard() {
		String board = "";

		for (int j = 0; j < gameBoard.length; j++) {
			for (int i = 0; i < gameBoard[j].length; i++) {
				board += gameBoard[j][i];
			}
			board += "\n";
		}

		return board;
	}

	public void stopGame() {
		gameStarted = false;
		gameTimer.stop();
		segments.clear();
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		if (messageContent.startsWith(COMMAND) && !gameStarted) {
			// respond to message here
			setup();
			try {
				gameChannel = event.getChannel();
				gameMessage = gameChannel.sendMessage(createEmbed()).get();
				gameMessage.addReactions(emojis);
				player = event.getMessage().getUserAuthor().get();

				gameTimer.start();
				gameStarted = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

		} else if (messageContent.startsWith(COMMAND)) {
			event.getChannel().sendMessage("Game Already Started!");
		} else if (messageContent.equals("!stop") && gameStarted) {
			stopGame();
			event.getChannel().sendMessage("Game Stopped");
		}
	}

	void changeHeadDirection() {
		if (checkForPlayerReact(0) && direction != 2) {
			direction = 4;
			gameMessage.removeReactionByEmoji(player, emojis[0]);
		} else if (checkForPlayerReact(1) && direction != 3) {
			direction = 1;
			gameMessage.removeReactionByEmoji(player, emojis[1]);
		} else if (checkForPlayerReact(2) && direction != 1) {
			direction = 3;
			gameMessage.removeReactionByEmoji(player, emojis[2]);
		} else if (checkForPlayerReact(3) && direction != 4) {
			direction = 2;
			gameMessage.removeReactionByEmoji(player, emojis[3]);
		}

		// The removeReactionByEmoji methods require the bot to have the "Megabot" role
		// in the Level 6 Discord Bot Chat Server to work
		// Otherwise it receives error code 503 that says missing permissions
	}

	boolean checkForPlayerReact(int emojiNum) {
		try {
			if (gameMessage.getReactionByEmoji(emojis[emojiNum]).get().getUsers().get().contains(player)) {
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// Execution Exception cannot be tested because constructor is hidden
			e.printStackTrace();
		}
		return false;
	}

	void moveHead() {
		if (direction == 1) {
			head.setYPos(head.getYPos() - 1);
		} else if (direction == 2) {
			head.setXPos(head.getXPos() + 1);
		} else if (direction == 3) {
			head.setYPos(head.getYPos() + 1);
		} else if (direction == 4) {
			head.setXPos(head.getXPos() - 1);
		}
	}

	boolean checkHeadPos() {
		if (head.getXPos() > 7 || head.getXPos() < 0 || head.getYPos() > 7 || head.getYPos() < 0) {
			return false;
		} else if (checkIfHitSelf()) {
			return false;
		} else {
			return true;
		}
	}

	boolean checkIfHitSelf() {
		for (int i = 1; i < segments.size(); i++) {
			if (head.getXPos() == segments.get(i).getXPos() && head.getYPos() == segments.get(i).getYPos()) {
				return true;
			}
		}
		return false;
	}

	void updateSnakePositionOnBoard() {
		for (int i = 0; i < segments.size(); i++) {
			gameBoard[segments.get(i).getYPos()][segments.get(i).getXPos()] = ":blue_square:";
		}
	}

	boolean checkIfEatFood() {
		if (head.getXPos() == foodX && head.getYPos() == foodY) {
			return true;
		} else {
			return false;
		}
	}

	void spawnFood() {
		Random r = new Random();
		boolean check = true;

		// Cannot test the food spawning due to randomness
		while (check) {
			foodX = r.nextInt(8);
			foodY = r.nextInt(8);
			check = false;

			for (int i = 0; i < segments.size(); i++) {
				if (foodX == segments.get(i).getXPos() && foodY == segments.get(i).getYPos()) {
					check = true;
					break;
				}
			}
			if (!check) {
				gameBoard[foodY][foodX] = ":orange_square:";
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(gameTimer)) {
			SnakeSegment lastSegment = segments.get(segments.size() - 1);
			int lastSegmentXPos = lastSegment.getXPos();
			int lastSegmentYPos = lastSegment.getYPos();

			for (int i = segments.size() - 1; i > 0; i--) {
				segments.get(i).followNextSegment(segments.get(i - 1));
			}

			if (checkIfEatFood()) {
				segments.add(new SnakeSegment(lastSegmentXPos, lastSegmentYPos));
				if(segments.size() < 64) {
					spawnFood();
				}
			} else {
				gameBoard[lastSegmentYPos][lastSegmentXPos] = ":green_square:";
			}

			changeHeadDirection();
			int prevHeadXPos = head.getXPos();
			int prevHeadYPos = head.getYPos();
			moveHead();

			if (!checkHeadPos()) {
				stopGame();
				gameBoard[prevHeadYPos][prevHeadXPos] = ":red_square:";
				gameChannel.sendMessage("Ouch! Game Over!");
			} else if(segments.size() == 64) {
				updateSnakePositionOnBoard();
				stopGame();
				gameChannel.sendMessage("You... won. How?");
			} else {
				updateSnakePositionOnBoard();
			}

			gameMessage.edit(createEmbed());

		}
	}
	
	int getDirection() {
		return direction;
	}
	
	String[][] getGameboard(){
		return gameBoard;
	}
	
	void setGameStarted(boolean started) {
		gameStarted = started;
	}
	
	void setTimer(Timer timer) {
		gameTimer = timer;
	}
	
	void setChannel(TextChannel channel) {
		gameChannel = channel;
	}
	
	void setPlayer(User user) {
		player = user;
	}
	
	void setGameMessage(Message message) {
		gameMessage = message;
	}
	
	void setDirection(int direction) {
		this.direction = direction;
	}
	
	void setSnakeSegementsArray(ArrayList<SnakeSegment> segments) {
		this.segments = segments;
	}
	
	void setHead(SnakeSegment head) {
		this.head = head;
	}
	
	void setFoodX(int foodX) {
		this.foodX = foodX;
	}
	
	void setFoodY(int foodY) {
		this.foodY = foodY;
	}

}
