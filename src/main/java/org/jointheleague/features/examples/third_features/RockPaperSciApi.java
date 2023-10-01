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


public class RockPaperSciApi extends Feature {

    public final String IMAGE_COMMAND = "!rps";
    private WebClient webClient;
    private static final String baseUrl = "";
    
	
    public RockPaperSciApi(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                IMAGE_COMMAND,
                "Use this method to play rock, paper, scissors!"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        //System.out.println("Inside method");
        if (messageContent.contains(IMAGE_COMMAND)) {
            //respond to message here!
       	 	 System.out.println("Printing - Inside If");
             String getUserInput = event.getMessageContent().replaceAll("!rps ", "");
             String robotInput = "";
             String gameResult = "";
             Random ran = new Random();
             int random = ran.nextInt(3);
             //System.out.println(random);
             if(random == 0) {
            	 robotInput = "Rock";
             }
             if(random == 1) {
            	 robotInput = "Paper";
             }
             if(random == 2) {
            	 robotInput = "Scissors";
             }
            //System.out.println("Choosing bot output");
             
             event.getChannel().sendMessage(robotInput);
             
             if(getUserInput.equalsIgnoreCase("Rock") && robotInput.equals("Rock")) {
            	 gameResult = "Tie";
             }
             if(getUserInput.equalsIgnoreCase("Paper") && robotInput.equals("Rock")) {
            	 gameResult = "Win";
             }
             if(getUserInput.equalsIgnoreCase("Scissors") && robotInput.equals("Rock")) {
            	 gameResult = "Lose";
             }
             if(getUserInput.equalsIgnoreCase("Rock") && robotInput.equals("Scissors")) {
            	 gameResult = "Win";
             }
             if(getUserInput.equalsIgnoreCase("Rock") && robotInput.equals("Paper")) {
            	 gameResult = "Win";
             }
             if(getUserInput.equalsIgnoreCase("Paper") && robotInput.equals("Scissors")) {
            	 gameResult = "Lose";
             }
             if(getUserInput.equalsIgnoreCase("Paper") && robotInput.equals("Paper")) {
            	 gameResult = "Tie";
             }
             if(getUserInput.equalsIgnoreCase("Scissors") && robotInput.equals("Scissors")) {
            	 gameResult = "Tie";
             }
             if(getUserInput.equalsIgnoreCase("Scissors") && robotInput.equals("Paper")) {
            	 gameResult = "Win";
             }
         
             event.getChannel().sendMessage(gameResult);
		}
        
    }

}
