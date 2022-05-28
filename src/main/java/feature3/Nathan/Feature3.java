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
    int i=-1;
    
    public Feature3(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This is a game where the player can buy items using money. When !balance is entered, the amount of money the user has will be displayed."
                + " To earn money, type in !work and you will be shown a food item and you will guess the cuisine the food item is from by saying !guess + (your guess)."
                + " To shop, type !shop and you will be given a list of items to buy. To buy an item type in !buy (the item you want to buy)"
        		);
        //upgrades? hints? guesses for work? categories? what will the user do with the items? what's the objective?
        //for the work game, right now, the user has to type in !work for every guess. Maybe change it so that the user will be shown a new image automatically.
    }
    
    public void distinctRandom() {
		for (int i = 0; i < 10;) {
			foodNumber = r.nextInt(10);
			if (!values.contains(foodNumber)) {
				values.add(foodNumber);
				i++;
			}
		}
		}
    
    public void makingFoodList() {
    	distinctRandom();
    	for(int i=0; i<10; i++) {
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
        		else if(values.get(i)==5) {
                	foodList.add("https://tinyurl.com/4fkam3pc");
                }
        		
        		
        	//italian
        		else if(values.get(i)==3) {
        	foodList.add("https://tinyurl.com/3ezycxb4");
        		}
        		else if(values.get(i)==6) {
                	foodList.add("");
                		}
        		
        	//chinese
        		else if(values.get(i)==4) {
        	foodList.add("https://tinyurl.com/56wbwy29");
        		}
        	else if(values.get(i)==7) {
            	foodList.add("");
            		}
        		
        		//korean
        	else if(values.get(i)==8) {
            	foodList.add("");
            		}
        	else if(values.get(i)==9) {
            	foodList.add("");
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
        
        else if(messageContent.startsWith("!work")) {
        	if(i==-1) {
        		i++;
        		makingFoodList();
        		event.getChannel().sendMessage(foodList.get(i));
        	}
        	
        	else if(i!=foodList.size()-1) {
        	i++;
        	event.getChannel().sendMessage(foodList.get(i));
        	}
        	
        	else if(i==foodList.size()-1) {
        		i=0;
        		makingFoodList();
        		event.getChannel().sendMessage(foodList.get(i));
        	}
        	
        }
        
        else if(messageContent.startsWith("!guess")) {
        			String guessMessage = messageContent.replaceAll(" ", "").replace("!guess", "");
        			interpretGuess(event, guessMessage);
        		}
        
       /* else {
        	event.getChannel().sendMessage("An error occurred");
        } */
        
    }
    
    public void interpretGuess(MessageCreateEvent event, String guess) {
    	if(foodList.get(i).equals("https://tinyurl.com/4xk9eff3")) {
			if(guess.equalsIgnoreCase("American")) {
				event.getChannel().sendMessage("Correct! You earned $5!");
				currentBalance+=5;
			}
			else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is American");
			}
		}
		
		else if(foodList.get(i).equals("https://tinyurl.com/28ecpmpz")) {
			if(guess.equalsIgnoreCase("American")) {
				event.getChannel().sendMessage("Correct! You earned $5!");
				currentBalance+=5;
			}
			else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is American");
			}
		}
		
		else if(foodList.get(i).equals("https://tinyurl.com/mvybcvte")) {
			if(guess.equalsIgnoreCase("Mexican")) {
				event.getChannel().sendMessage("Correct! You earned $5!");
				currentBalance+=5;
			}
			else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Mexican");
			}
		}
		
		else if(foodList.get(i).equals("https://tinyurl.com/3ezycxb4")) {
			if(guess.equalsIgnoreCase("Italian")) {
				event.getChannel().sendMessage("Correct! You earned $5!");
				currentBalance+=5;
			}
			else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Italian");
			}
		}
		
		else if(foodList.get(i).equals("https://tinyurl.com/56wbwy29")) {
			if(guess.equalsIgnoreCase("Chinese")) {
				event.getChannel().sendMessage("Correct! You earned $5!");
				currentBalance+=5;
			}
			else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Chinese");
			}
		}
		}
    
      

}