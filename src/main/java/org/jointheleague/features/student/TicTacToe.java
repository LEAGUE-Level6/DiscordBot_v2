package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class TicTacToe extends Feature {

    public final String COMMAND = "!tictactoe";
    private boolean inProgress;
    private String players[];
    private int board[][] = new int[3][3];
    private int currentPlayer;

    public TicTacToe(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Allows two users to play a game of tic-tac-toe"
        );
        reset();

    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if(inProgress) {
        	if(messageContent.equals(COMMAND)) {
            	event.getChannel().sendMessage("There is currently an ongoing game!");
        	}
        	if(messageContent.equals("!p1")) {
        		players[0] = event.getMessageAuthor().getName();
        		event.getChannel().sendMessage("Player 1: " + players[0]);
	        	if(!players[1].equals("")) {
	        		sendStartMessage(event);
	        	}
	        }else if(messageContent.equals("!p2")){
	
		        	players[1] = event.getMessageAuthor().getName();
	        		event.getChannel().sendMessage("Player 2: " + players[1]);
	
		        	if(!players[0].equals("")) {
		        		sendStartMessage(event);
		        	}
	        	//}
	        }else if(messageContent.startsWith("!place")) {
	        	//if(event.getMessageAuthor().toString().equals(currentPlayer)) {
	        		int x = -1;
	        		int y = -1;
	        		boolean valid = true;
	        		try {
	        			x = Integer.parseInt(messageContent.charAt(8) + "");
	        			y = Integer.parseInt(messageContent.charAt(11) + "");
	        		}catch(Exception e) {
	        			event.getChannel().sendMessage("Invalid Format!");
	        			valid = false;
	        		}
	        		if(valid) {
		        		if(x >= 0 && x <= 2 && y >= 0 && y <= 2) {
		        			if(board[x][y] == -1) {
		        				board[x][y] = currentPlayer;
		        				if(currentPlayer == 0) {
		        					currentPlayer = 1;
		        				}else {
		        					currentPlayer = 0;
		        				}
		        				displayBoard(event);
		        				int winner = checkWin();
		        				if(winner == -1) {
		        					event.getChannel().sendMessage(players[currentPlayer] + ", it is your turn");
		        				}else {
		        					event.getChannel().sendMessage(players[winner] + ", you have won!");
		        					reset();
		        				}
		        			}else {
		        				event.getChannel().sendMessage("That spot is already taken!");
		        			}
		        		}
	        			
	        		}else {
	        			event.getChannel().sendMessage("Invalid coordinates!");
	        		}
	        		
	        	//}else {
	        	//	event.getChannel().sendMessage("It is not your turn!");
	        	//}
	        	
	        }
        }
        
        if(messageContent.equals(COMMAND)){
        	inProgress = true;
        	event.getChannel().sendMessage("Game Initiated!\nPlayer 1, enter the command: !p1");
        	event.getChannel().sendMessage("Player 2, enter the command: !p2");
        
        }
        
    }
    private void reset() {
    	for(int i = 0; i <= 2; i++) {
        	for(int j = 0; j <= 2; j++) {
        		board[i][j] = -1;
        	}
        }
    	players = new String[2];
    	currentPlayer = 0;
    	inProgress = false;
    }
    private void sendStartMessage(MessageCreateEvent e) {
    	currentPlayer = 0;
    	e.getChannel().sendMessage("To play, enter the coordinate position of where you would like to play as a coordinate pair, ex: (1, 2)\nThe bottom right of the board is (0, 0), and the top right of the board is (2, 2)\nFor example, the X is at (1, 2)\n"
    			+ "\n  | X |  "
    			+ "\n-------"
    			+ "\n  |    |  "
    			+ "\n-------"
    			+ "\n  |    | "
    			+ "\n" + players[currentPlayer] + ", make your move using !place ({x}, {y})");
    }
    private void displayBoard(MessageCreateEvent e) {

    	e.getChannel().sendMessage("Board: ");
    	String display = "";
    	for(int i = 2; i >= 0; i--) {
    		//e.getChannel().sendMessage("I: " + i);
    		for(int j = 0; j <= 2; j++) {
    			//e.getChannel().sendMessage("J: " + j);
    			
    			if(board[j][i] == -1) {
    				display += "   ";
    			}else {
    				display += (board[j][i] == 0 ? "X" : "O");
    			}
    			
//    			display += "X";
    			
    			if(j != 2) {
    				display += "|";
    			}
    		}
    		if(i != 0) {
    			display += "\n--------\n";
    		}
    	}
    	e.getChannel().sendMessage(display);
    }
    private int checkWin() {
    	//check sideways
    	if(board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
    		return board[0][0];
    	}
    	if(board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
    		return board[2][0];
    	}
    	if(board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
    		return board[2][0];
    	}
    	
    	if(board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
    		return board[0][0];
    	}
    	if(board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
    		return board[0][1];
    	}
    	if(board[0][2] == board[1][2] && board[1][0] == board[2][2]) {
    		return board[0][2];
    	}
    	
    	//check diag
    	if(board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
    		return board[0][0];
    	}else if(board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
    		return board[0][2];
    	}
    	
    	return -1;
    }
    
}
