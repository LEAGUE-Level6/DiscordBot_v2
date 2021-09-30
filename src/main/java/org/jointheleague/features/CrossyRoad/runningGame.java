package org.jointheleague.features.CrossyRoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

public class runningGame implements Runnable, ActionListener, ReactionAddListener, ReactionRemoveListener{
	volatile boolean dead = false;
	public boolean move = false;
	boolean canMove = true;
	int score = 0;
	Row[] board;
	Timer game;
	MessageAuthor player;
	MessageCreateEvent toEdit;
	public runningGame(Row[] board, MessageCreateEvent toEdit,MessageAuthor player) {
	this.board = board;
	this.toEdit = toEdit;
	this.player = player;
	game = new Timer(1250, this);
	toEdit.getMessage().addReaction("⏫");
	toEdit.getMessage().addReaction("❌");
	toEdit.getMessage().addReactionAddListener(this);
	toEdit.getMessage().addReactionRemoveListener(this);
	}
	public void kill() {
	dead = true;
	}
	@Override
	public void run() {
	game.start();
	while(!dead) {
	}
	game.stop();
	toEdit.getMessage().removeListener(ReactionAddListener.class, this);
	toEdit.getMessage().removeListener(ReactionRemoveListener.class, this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//What needs to happen is I need to find a way to interact with my user, most likely by sending any message that is not a command to move the bird
		//Most likely just need to work on some for loops etc
		if(!move) {
		for(int i = 0; i < board.length-1; i++) {
			dead = board[i].update(false);
		}
		dead = board[6].update(true);
		} else {
			move = false;
		for(int i = board.length-1; i > 0;i--) {
			board[i] = board[i-1];
			if(i == 6) {
			dead = board[i].update(true);
			}
		}
		score++;
		int x = new Random().nextInt(10);
		if(x < 5) {
		board[0] = new Row(0,false);
		} else if (x < 7) {
			board[0] = new Row(1,false);
		} else if (x < 9) {
			board[0] = new Row(2,false);
		} else {
			board[0] = new Row(3,false);
		}
		}
		String send = "";
		for(int i = 0; i < board.length;i++) {
			for(int w = 0; w < board[i].gameRow.length;w++) {
				send +=board[i].gameRow[w];
			}
			send+="\n";
		}
		send+= "\n Current score is: "+score+"\n"+"\n"+"\n"+"\n Click ⏫ to move up and ❌ to quit";
	toEdit.editMessage(send);
	canMove = true;
	}
	@Override
	public void onReactionAdd(ReactionAddEvent event) {
	//continue here
	
		String e = event.getEmoji().asUnicodeEmoji().get();
	if(e.equals("❌")&&player.getId()==event.getUserId()) {
		kill();
	} else if (e.equals("⏫")&&player.getId()==event.getUserId()) {
		if(canMove) {
		move = true;
		canMove = false;
		}
	}
	}
	@Override
	public void onReactionRemove(ReactionRemoveEvent event) {
		// TODO Auto-generated method stub
		String e = event.getEmoji().asUnicodeEmoji().get();
		if (e.equals("⏫")&&player.getId()==event.getUserId()) {
			if(canMove) {
			move = true;
			canMove = false;
			}
			}
	}

}
