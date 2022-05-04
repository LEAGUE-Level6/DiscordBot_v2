package org.jointheleague;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.jointheleague.features.abstract_classes.ReactionFeature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import com.vdurmont.emoji.EmojiParser;

import net.aksingh.owmjapis.api.APIException;

public class SnakeGame extends ReactionFeature {

    public final String COMMAND = "!snake";
    public Message m;
    public boolean inSnakeGame = false;
    public boolean inSnakeGameStartup = false;
    public final User bot;
    private Snake snake;
    private Body apple;
    private boolean inUpdate = false;

    public SnakeGame(String channelName, User _bot) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Creates a snake game");
        bot=_bot;
    }

    @Override
    public void handle(MessageCreateEvent event) {
    	String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)&&!inSnakeGame) {
        	System.out.println("Snake Game Started!");
			inSnakeGame = true;
			inSnakeGameStartup = true;
			event.getChannel().sendMessage("Snake Game Image building...");
        }
        
        else if(event.getMessage().getAuthor().isBotUser()&&event.getMessage().getAuthor().asUser().get().equals(bot)&&inSnakeGameStartup){
        	snake = new Snake(5, 5, 3, Direction.UP);
        	generateApple();
        	inSnakeGameStartup = false;
        	m = event.getMessage();
        	m.edit(drawBoard());
			m.addReaction("⬆️");
			m.addReaction("⬇️");
			m.addReaction("⬅️");
			m.addReaction("➡️");
        }
        else if(event.getMessage().getAuthor().isBotUser()&&event.getMessage().getAuthor().asUser().get().equals(bot)&&inUpdate) {
        	m.delete();
        	m = event.getMessage();
			m.addReaction("⬆️");
			m.addReaction("⬇️");
			m.addReaction("⬅️");
			m.addReaction("➡️");
	        inUpdate = false;
        }
        else {
        	System.out.println(event.getMessageContent());
        }
    }
    
    String drawBoard() {
		String board = "";
			int[][] GameBoard = new int[11][11];
			for (Body b : snake.getBody()) {
				GameBoard[b.getX()][b.getY()] = 1;
			}
            for (int _x = 0; _x < 11; _x++) {
            	for (int _y = 0; _y < 11; _y++) {
					if(GameBoard[_y][_x]==1) {
						board+="🟦";
					}
					else if(_x==apple.getX()&&_y==apple.getY()) {
						board+="🟥";
					}
					else {
						board+="🟩";
					}
				}
            	board+="\n";
            }
    	return board;
    }

    private void generateApple() {
        Random random = new Random();
        Body body;

        do {
        	body = new Body(random.nextInt(11), random.nextInt(11));
        } while (isOccupied(body));

        apple = body;
    }

    private boolean isOccupied(Body body) {
        if (body.getX() == 5 || body.getY() == 5) {
            return true;
        }
        if (snake.getBody().contains(body)) {
        	return true;
        }
        return false;
    }
    
    private void move(Emoji e, ReactionAddEvent event) {
        inUpdate = true;
        if (e.equalsEmoji("⬆️")
        		) {
        	if(snake.getBody().getFirst().getY()==0) {
        		snake.setCrashed(true);
        	}
        	snake.makeStep(Direction.UP);
        }

        else if (e.equalsEmoji("➡️")
        		) {
        	if(snake.getBody().getFirst().getX()>=10) {
        		snake.setCrashed(true);
        	}
        	snake.makeStep(Direction.RIGHT);
        }

        else if (e.equalsEmoji("⬅️")
        		) {
        	if(snake.getBody().getFirst().getX()==0) {
        		snake.setCrashed(true);
        	}
        	snake.makeStep(Direction.LEFT);
        }

        else if (e.equalsEmoji("⬇️")
        		) {
        	if(snake.getBody().getFirst().getY()>=10) {
        		snake.setCrashed(true);
        	}
        	snake.makeStep(Direction.DOWN);
        }
        System.out.println(apple.getX());
        System.out.println(apple.getY());
        System.out.println(snake.getBody().getFirst().getX());
        System.out.println(snake.getBody().getFirst().getY());
        for (Body segment : snake.getBody()) {
			if(segment.getY()==apple.getX()&&segment.getX()==apple.getY()) {
				snake.setEat(true);
	        	generateApple();
			}
		}
        if(!snake.isCrashed()) {
    		event.getChannel().sendMessage(drawBoard());
        }
        else {
        	inUpdate = false;
        	m.delete();
    		event.getChannel().sendMessage("You crashed. :(");
    	    inSnakeGame = false;
    	    inSnakeGameStartup = false;
        }
    }

	@Override
	public void handleReaction(ReactionAddEvent event) throws APIException {
		if(m!=null) {
			if(m.getId()==event.getMessageId()) {
				try {
					if(!event.getUser().isPresent()||!event.getUser().get().equals(bot)) {
						event.removeReaction();
						Emoji e = event.getEmoji();
						move(e, event);
					}
				} catch (Exception e) {
					event.removeReaction();
				}
			}
		}
	}

}
