package feature3.Nathan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature3 extends Feature {

    public final String COMMAND = "!balance";
    int currentBalance=0;
    ArrayList<String> foodList=new ArrayList<String>();
    int foodNumber;
    Random r=new Random();
    ArrayList<Integer> values = new ArrayList<Integer>();
    
    public Feature3(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "When !balance is entered, the amount of money the user has will be displayed."
                + "To earn money, type in !work and you will be shown a food item and you will guess the cuisine the food item is from by saying !guess + (your guess)"
                + "To shop, type !shop and you will be given a list of items to buy. To buy an item type in !buy (the item you want to buy)"
        		);
        //upgrades? hints? categories? what will the user do with the items? what's the objective?
    }
    
    public void distinctRandom() {
		for (int i = 0; i < 5;) {
			foodNumber = r.nextInt(5);
			if (!values.contains(foodNumber)) {
				values.add(foodNumber);
				i++;
			}
		}
		}
    
    public void makingFoodList() {
    	distinctRandom();
    	for(int i=0; i<5; i++) {
        	//american 
        		if(values.get(i)==0) {
        	foodList.add("https://tinyurl.com/4xk9eff3");
        		}
        		
        		else if(values.get(i)==1) {
        	foodList.add("https://tinyurl.com/28ecpmpz");
        		}
        		
        	//mexican
        		else if(values.get(i)==2) {
        	foodList.add("https://tinyurl.com/mvybcvte");
        		}
        		
        	//italian
        		else if(values.get(i)==3) {
        	foodList.add("https://tinyurl.com/3ezycxb4");
        		}
        		
        	//chinese
        		else if(values.get(i)==4) {
        	foodList.add("https://tinyurl.com/56wbwy29");
        		}
    	}
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            event.getChannel().sendMessage("Your current balance is $" + currentBalance);
        }
        
        if(messageContent.startsWith("!work")) {
        	makingFoodList();
        	String guess = messageContent.replaceAll(" ", "").replace("!guess", "");
        	
        	for(int i=0; i<foodList.size(); i++) {
        		if(foodList.get(i).equals("https://tinyurl.com/4xk9eff3")) {
        			if(guess.equals("American")) {
        				event.getChannel().sendMessage("Correct! You earned $5!");
        				currentBalance+=5;
        			}
        		}
        	}
        }
        	
        	
        }
      

}