
package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import static org.mockito.Mockito.when;

import java.util.Random;

public class BuzzfeedQuiz extends Feature {

    public final String COMMAND = "!buzzFeed";
    private final Random random = new Random();
    int numberToGuess;
    String messageContent="";
    MessageCreateEvent event;
    int pepperoniPizza=0;
    int ramen=0;
    int donut=0;
    int anchove=0;
    int totalResponses=ramen+pepperoniPizza+donut+anchove;
    
    public BuzzfeedQuiz(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "");
    }

    @Override
    public void handle(MessageCreateEvent e){
    	event=e;
         messageContent = event.getMessageContent();
       
while(totalResponses<5){
    if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("Figure out what food represents you by filling out this quiz! Write '!Buzzfeed' and your answer.");
            event.getChannel().sendMessage("What do you like to do during your free time?");
            event.getChannel().sendMessage("1. Sleep");
            event.getChannel().sendMessage("2. Sky dive");
            event.getChannel().sendMessage("3. Read a book");
            event.getChannel().sendMessage("4. Go to the beach");
        }
        //question 1
        else if (messageContent.contains("sleep")) {
        	donut ++;
            event.getChannel().sendMessage("If you were stuck on an island, who would you want to be stuck with?");
            event.getChannel().sendMessage("1. Mom");
            event.getChannel().sendMessage("2. Dad");
            event.getChannel().sendMessage("3. Best Friend");
            event.getChannel().sendMessage("4. Sibling");

        } else if (messageContent.contains("sky dive")) {
        	ramen ++;
        	event.getChannel().sendMessage("If you were stuck on an island, who would you want to be stuck with?");
            event.getChannel().sendMessage("1. Mom");
            event.getChannel().sendMessage("2. Dad");
            event.getChannel().sendMessage("3. Best Friend");
            event.getChannel().sendMessage("4. Sibling");
        } else if (messageContent.contains("read a book")) {
        	anchove ++;
        	event.getChannel().sendMessage("If you were stuck on an island, who would you want to be stuck with?");
            event.getChannel().sendMessage("1. Mom");
            event.getChannel().sendMessage("2. Dad");
            event.getChannel().sendMessage("3. Best Friend");
            event.getChannel().sendMessage("4. Sibling");
        } else if (messageContent.contains("go to the beach")) {
        	pepperoniPizza ++;
        	event.getChannel().sendMessage("If you were stuck on an island, who would you want to be stuck with?");
            event.getChannel().sendMessage("1. Mom");
            event.getChannel().sendMessage("2. Dad");
            event.getChannel().sendMessage("3. Best Friend");
            event.getChannel().sendMessage("4. Sibling");
        } 
    //question 2
        else if(messageContent.contains("Mom")) {
        	pepperoniPizza ++;
        	event.getChannel().sendMessage("Favorite subject in school:");
            event.getChannel().sendMessage("1. Math");
            event.getChannel().sendMessage("2. Science");
            event.getChannel().sendMessage("3. English ");
            event.getChannel().sendMessage("4. History");
        }
        else if(messageContent.contains("Dad")) {
        	ramen ++;
        	event.getChannel().sendMessage("Favorite subject in school:");
            event.getChannel().sendMessage("1. Math");
            event.getChannel().sendMessage("2. Science");
            event.getChannel().sendMessage("3. English ");
            event.getChannel().sendMessage("4. History");
        }else if(messageContent.contains("Best Friend")) {
        	donut ++;
        	event.getChannel().sendMessage("Favorite subject in school:");
            event.getChannel().sendMessage("1. Math");
            event.getChannel().sendMessage("2. Science");
            event.getChannel().sendMessage("3. English ");
            event.getChannel().sendMessage("4. History");
        }else if(messageContent.contains("Sibling")) {
        	anchove ++;
        	event.getChannel().sendMessage("Favorite subject in school:");
            event.getChannel().sendMessage("1. Math");
            event.getChannel().sendMessage("2. Science");
            event.getChannel().sendMessage("3. English ");
            event.getChannel().sendMessage("4. History");
        }
    //question 3
        else if(messageContent.contains("Math")) {
        	donut ++;
        	event.getChannel().sendMessage("Favorite sport:");
            event.getChannel().sendMessage("1. Soccer");
            event.getChannel().sendMessage("2. Volleyball");
            event.getChannel().sendMessage("3. Field Hockey ");
            event.getChannel().sendMessage("4. Surfing");
        }else if(messageContent.contains("Science")) {
        	ramen ++;
        	event.getChannel().sendMessage("Last question... Favorite sport:");
            event.getChannel().sendMessage("1. Soccer");
            event.getChannel().sendMessage("2. Volleyball");
            event.getChannel().sendMessage("3. Field Hockey ");
            event.getChannel().sendMessage("4. Surfing");
        }else if(messageContent.contains("English")) {
        	pepperoniPizza ++;
        	event.getChannel().sendMessage("Last question... Favorite sport:");
            event.getChannel().sendMessage("1. Soccer");
            event.getChannel().sendMessage("2. Volleyball");
            event.getChannel().sendMessage("3. Field Hockey ");
            event.getChannel().sendMessage("4. Surfing");
        }else if(messageContent.contains("History")) {
        	anchove ++;
        	event.getChannel().sendMessage("Last question... Favorite sport:");
            event.getChannel().sendMessage("1. Soccer");
            event.getChannel().sendMessage("2. Volleyball");
            event.getChannel().sendMessage("3. Field Hockey ");
            event.getChannel().sendMessage("4. Surfing");
        }
    //question 4
        else if(messageContent.contains("Soccer")) {
        	donut ++;
        	event.getChannel().sendMessage("Dream Vacation spot:");
            event.getChannel().sendMessage("1. Italy");
            event.getChannel().sendMessage("2. Bora Bora");
            event.getChannel().sendMessage("3. Tanzania");
            event.getChannel().sendMessage("4. Palm Springs");
        }else if(messageContent.contains("Volleyball")) {
        	ramen ++;
        	event.getChannel().sendMessage("Dream Vacation spot:");
            event.getChannel().sendMessage("1. Italy");
            event.getChannel().sendMessage("2. Bora Bora");
            event.getChannel().sendMessage("3. Tanzania");
            event.getChannel().sendMessage("4. Palm Springs");
        }else if(messageContent.contains("Field Hockey")) {
        	anchove ++;
        	event.getChannel().sendMessage("Dream Vacation spot:");
            event.getChannel().sendMessage("1. Italy");
            event.getChannel().sendMessage("2. Bora Bora");
            event.getChannel().sendMessage("3. Tanzania");
            event.getChannel().sendMessage("4. Palm Springs");
        }else if(messageContent.contains("Surfing")) {
        	pepperoniPizza ++;
        	event.getChannel().sendMessage("Dream Vacation spot:");
            event.getChannel().sendMessage("1. Italy");
            event.getChannel().sendMessage("2. Bora Bora");
            event.getChannel().sendMessage("3. Tanzania");
            event.getChannel().sendMessage("4. Palm Springs");
        }
    //question 5
        else if(messageContent.contains("Italy")) {
        	pepperoniPizza ++;
        } else if(messageContent.contains("Bora Bora")) {
        	ramen ++;
        } else if(messageContent.contains("Tanzania")) {
        	donut ++;
        } else if(messageContent.contains("Palm Springs")) {
        	anchove ++;
        }
}
event.getChannel().sendMessage("You have finished the quiz:");

    }
    
}
