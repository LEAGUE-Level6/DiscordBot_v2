package org.jointheleague.features.CrossyRoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.javacord.api.event.message.MessageCreateEvent;

public class runningGame implements Runnable, ActionListener{
	public boolean dead = false;
	Row[] board;
	Timer game;
	MessageCreateEvent toEdit;
	public runningGame(Row[] board, MessageCreateEvent toEdit) {
	this.board = board;
	this.toEdit = toEdit;
	game = new Timer(100, this);
	}
	public void kill() {
	dead = true;
	}
	@Override
	public void run() {
	game.start();
	while(!dead) {
	}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//What needs to happen is I need to find a way to interact with my user, most likely by sending any message that is not a command to move the bird
		//Most likely just need to work on some for loops etc
		String send = "";
		for(int i = 0; i < board.length;i++) {
			for(int w = 0; w < board[i].gameRow.length;w++) {
				send +=board[i].gameRow[w];
			}
			send+="\n";
		}
	toEdit.editMessage(send);
	}

}
