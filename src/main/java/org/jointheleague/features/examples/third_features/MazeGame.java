package org.jointheleague.features.examples.third_features;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.Channel;


public class MazeGame extends Feature {

    public final String IMAGE_COMMAND = "!MazeGame";
    private WebClient webClient;
    int row = 4;
	int col = 0;
    private static final String baseUrl = "";
    String[][] Array = {{"⬜","⬛","⬛","⬛","⬛","🟩"},
			{"⬛","⬛","⬛","⬛","⬛","⬛"},
			{"⬛","⬛","⬜","⬛","⬜","⬜"},
			{"⬛","⬛","⬛","⬛","⬛","⬜"},
			{"🟨","⬜","⬜","⬜","⬛","⬛"}};
    String pos = Array[row][col];
	
    public MazeGame(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                IMAGE_COMMAND,
                "Use this method to a maze game!"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
    	
        String messageContent = event.getMessageContent();
        if (messageContent.contains(IMAGE_COMMAND)) {
            //respond to message here!
        	String maze = "";
        	for (int i = 0; i < Array.length; i++) {
        		 
                // Loop through all elements of current row
                for (int j = 0; j < Array[i].length; j++) {
                	maze += Array[i][j] + " ";
                	
                } 
                maze = maze + "\n";
                
        	}
        	event.getChannel().sendMessage(maze);
		}
        String message = event.getMessageContent();
        if (message.contains(IMAGE_COMMAND)) {
        	
            //respond to message here!
        	String direction = event.getMessageContent().replaceAll("!MazeGame ", "");
        	if(direction.equalsIgnoreCase("left")) {
        		//1 Check if can move (barrier or goes out of bounds)
        		//2 If can't, print can't
        		String oldpos = Array[row][col];
        		//3 If can, replace that square with black, and the new one with yello
        		if(col-1 >= 0) {
        			col = col-1;
        		}
        		else {
        			event.getChannel().sendMessage("You can't move here! Out of bounds");
        		}
        		String newpositon = Array[row][col];
        		
        		if(newpositon == "⬛") {
        			Array[row][col]= "🟨";
        			Array[row][col+1] = "⬛";
        			
        			event.getChannel().sendMessage(printMaze(Array));
        		}
        		else if(newpositon == "🟩") {
        			event.getChannel().sendMessage("You won!");
        		}
        		
        		
        	}
        	if(direction.equalsIgnoreCase("right")) {
        		String oldpos = Array[row][col];
        		System.out.println("Array val:" + Array[row][0].length());
        		//3 If can, replace that square with black, and the new one with yello
        		if(col+1 < 6) {
        			col = col+1;
        		}
        		else {
        			event.getChannel().sendMessage("You can't move here! Out of bounds");
        		}
        		String newpositon = Array[row][col];
        		
        		if(newpositon == "⬛") {
        			
        			Array[row][col] = "🟨";
        			Array[row][col-1] = "⬛";
        			
        			event.getChannel().sendMessage(printMaze(Array));
        		}
        		else if(newpositon == "🟩") {
        			event.getChannel().sendMessage("You won!");
        		}
        		System.out.println("Col: " + col); 
        		
        	}
        	if(direction.equalsIgnoreCase("up")) {
        		String oldpos = Array[row][col];
        		System.out.println(row);
        		//3 If can, replace that square with black, and the new one with yello
        		if(row-1 >= 0) {
        			row = row-1;
        			
        		}
        		else {
        			event.getChannel().sendMessage("You can't move here! Out of bounds.");
        			
        			
        		}
        		String newpositon = Array[row][col];
        		
        		if(newpositon == "⬛") {
        			Array[row][col] = "🟨";
        			Array[row+1][col] = "⬛";
        			
        			
        		}
        		else if(newpositon == "🟩") {
        			event.getChannel().sendMessage("You won!");
        		}
        		
        		event.getChannel().sendMessage(printMaze(Array));
        		System.out.println(row);
        		
        	}
        	if(direction.equalsIgnoreCase("down")) {
        		String oldpos = Array[row][col];
        		//3 If can, replace that square with black, and the new one with yello
        		if(row-1 < Array[0][col].length()) {
        			row = row+1;
        		}
        		else {
        			event.getChannel().sendMessage("You can't move here! Out of bounds");
        		}
        		String newpositon = Array[row][col];
        		
        		if(newpositon == "⬛") {
        			
        			Array[row][col] = "🟨";
        			Array[row-1][col] = "⬛";
        			
        			event.getChannel().sendMessage(printMaze(Array));
        		}
        		
        		else if(newpositon == "🟩") {
        			event.getChannel().sendMessage("You won!");
        		}
        		
        		
        	}

		}
        
        
        
    }
    public String printMaze(String[][] Array) {
    	String maze = "";
    	for (int i = 0; i < Array.length; i++) {
   		 
            // Loop through all elements of current row
            for (int j = 0; j < Array[i].length; j++) {
            	
            	maze += Array[i][j]+"";
            } 
            maze+= "\n";
    	}
    	return maze;
	}
    }
