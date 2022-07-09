package feature3.Nathan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature3 extends Feature {

	public final String COMMAND = "!balance";
	int currentBalance = 0;
	ArrayList<String> foodList = new ArrayList<String>();
	int foodNumber;
	Random r = new Random();
	ArrayList<Integer> values = new ArrayList<Integer>();
	int i = 0;
	int j=0;
	ArrayList<String> itemsList = new ArrayList<String>();
	int moneyAdded = 10;

	public Feature3(String channelName) {
		super(channelName);
		makingItemsList();
		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"This is a game where the player can buy items using money. When !balance is entered, the amount of money the user has will be displayed."
						+ " To earn money, type in !work and you will be shown a food item and you will guess the cuisine the food item is from by saying !guess + (your guess). You earn $10 from each correct guess (without upgrades)."
						+ " To shop, type !shop and you will be given a list of items and upgrades to buy. To buy an item type in !buy (the item you want to buy).");
	}

	public void distinctRandom() {
		values.clear();
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
		foodList.clear();
		for (int i = 0; i < 10; i++) {
			// american
			if (values.get(i) == 0) {
				foodList.add("https://tinyurl.com/4xk9eff3");
			} else if (values.get(i) == 1) {
				foodList.add("https://tinyurl.com/28ecpmpz");
			}

			// mexican
			else if (values.get(i) == 2) {
				foodList.add("https://tinyurl.com/mvybcvte");
			} else if (values.get(i) == 3) {
				foodList.add("https://tinyurl.com/4fkam3pc");
			}

			// italian
			else if (values.get(i) == 4) {
				foodList.add("https://tinyurl.com/3ezycxb4");
			} else if (values.get(i) == 5) {
				foodList.add("https://tinyurl.com/ybrv2dvb");
			}

			// chinese
			else if (values.get(i) == 6) {
				foodList.add("https://tinyurl.com/56wbwy29");
			} else if (values.get(i) == 7) {
				foodList.add("https://tinyurl.com/2nr9k8sc");
			}

			// korean
			else if (values.get(i) == 8) {
				foodList.add("https://tinyurl.com/abcndwh7");
			} else if (values.get(i) == 9) {
				foodList.add("https://tinyurl.com/mrwf8xa7");
			}

		}
	}

	public void makingItemsList() {
		itemsList.add("clothing $20");
		itemsList.add("shoes $40");
		itemsList.add("chair $100");
		itemsList.add("smart watch $200");
		itemsList.add("iphone $400");
		itemsList.add("TV $700");
		itemsList.add("computer $1000");
		itemsList.add("2x more money upgrade $50");
		itemsList.add("3x more money upgrade $140");
		itemsList.add("4x more money upgrade $240");
		itemsList.add("5x more money upgrade $500");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		

		if (messageContent.startsWith(COMMAND)) {
			// respond to message here
			event.getChannel().sendMessage("Your current balance is $" + currentBalance);
		}

		else if (messageContent.startsWith("!work")) {
			//check foodListt.size()
			if (i == 0) {
				makingFoodList();
				event.getChannel().sendMessage(foodList.get(i));
				i++;
			}

			else if (i < foodList.size() - 1) {
				event.getChannel().sendMessage(foodList.get(i));
				i++;
			}

			else if (i == foodList.size() - 1) {
				event.getChannel().sendMessage(foodList.get(i));
				i = 0;
			}
			
			
		}

		else if (messageContent.startsWith("!guess")) {
			String guessMessage = messageContent.replaceAll(" ", "").replace("!guess", "");
			interpretGuess(event, guessMessage);
		}

		else if (messageContent.startsWith("!shop")) {
			StringBuffer sb = new StringBuffer();
			sb.append(itemsList);
			String currentList = sb.toString();
			//messageContent="";
			event.getChannel().sendMessage(currentList);
		}

		else if (messageContent.startsWith("!buy")) {
			String buyMessage = messageContent.replaceAll(" ", "").replace("!buy", "");
			interpretPurchase(event, buyMessage);
		}


	}

	public void interpretGuess(MessageCreateEvent event, String guess) {
		if (foodList.get(j).equals("https://tinyurl.com/4xk9eff3")) {
			if (guess.equalsIgnoreCase("American")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is American");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/28ecpmpz")) {
			if (guess.equalsIgnoreCase("American")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is American");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/mvybcvte")) {
			if (guess.equalsIgnoreCase("Mexican")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Mexican");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/4fkam3pc")) {
			if (guess.equalsIgnoreCase("Mexican")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Mexican");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/3ezycxb4")) {
			if (guess.equalsIgnoreCase("Italian")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Italian");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/ybrv2dvb")) {
			if (guess.equalsIgnoreCase("Italian")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Italian");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/56wbwy29")) {
			if (guess.equalsIgnoreCase("Chinese")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Chinese");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/2nr9k8sc")) {
			if (guess.equalsIgnoreCase("Chinese")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Chinese");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/abcndwh7")) {
			if (guess.equalsIgnoreCase("Korean")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Korean");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}

		else if (foodList.get(j).equals("https://tinyurl.com/mrwf8xa7")) {
			if (guess.equalsIgnoreCase("Korean")) {
				event.getChannel().sendMessage("Correct! You earned $" + moneyAdded);
				currentBalance += moneyAdded;
			} else {
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is Korean");
			}
			if(j<foodList.size()-1) {
				j++;
			}
			else if(j==foodList.size()-1) {
				j=0;
			}
		}
	}

	public void interpretPurchase(MessageCreateEvent event, String item) {
		if (item.equalsIgnoreCase("clothing")) {
			if(currentBalance>=20) {
			event.getChannel().sendMessage("The clothing has been bought");
			itemsList.remove("clothing $20");
			currentBalance -= 20;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("shoes")) {
			if(currentBalance>=40) {
			event.getChannel().sendMessage("The shoes has been bought");
			itemsList.remove("shoes $40");
			currentBalance -= 40;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("chair")) {
			if(currentBalance>=100) {
			event.getChannel().sendMessage("The chair has been bought");
			itemsList.remove("chair $100");
			currentBalance -= 100;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("smart watch")) {
			if(currentBalance>=200) {
			event.getChannel().sendMessage("The smart watch has been bought");
			itemsList.remove("smart watch $200");
			currentBalance -= 200;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("iphone")) {
			if(currentBalance>=400) {
			event.getChannel().sendMessage("The iphone has been bought");
			itemsList.remove("iphone $400");
			currentBalance -= 400;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("TV")) {
			if(currentBalance>=700) {
			event.getChannel().sendMessage("The TV has been bought");
			itemsList.remove("TV $700");
			currentBalance -= 700;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("Computer")) {
			if(currentBalance>=1000) {
			event.getChannel().sendMessage("The computer has been bought");
			itemsList.remove("computer $1000");
			currentBalance -= 1000;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("2xmoremoneyupgrade")) {
			if(currentBalance>=50) {
			event.getChannel().sendMessage("The 2x upgrade has been bought");
			itemsList.remove("2x more money upgrade $50");
			currentBalance -= 50;
			moneyAdded=20;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("3xmoremoneyupgrade")) {
			if(currentBalance>=140) {
			event.getChannel().sendMessage("The 3x upgrade has been bought");
			itemsList.remove("3x more money upgrade $140");
			currentBalance -= 140;
			moneyAdded=30;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("4xmoremoneyupgrade")) {
			if(currentBalance>=240) {
			event.getChannel().sendMessage("The 4x upgrade has been bought");
			itemsList.remove("4x more money upgrade $240");
			currentBalance -= 240;
			moneyAdded=40;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else if (item.equalsIgnoreCase("5xmoremoneyupgrade")) {
			if(currentBalance>=500) {
			event.getChannel().sendMessage("The 5x upgrade has been bought");
			itemsList.remove("5x more money upgrade $500");
			currentBalance -= 500;
			moneyAdded=50;
			}
			else {
				event.getChannel().sendMessage("Sorry, you do not have enough money to purchase this item");
			}
		}
		
		else {
			event.getChannel().sendMessage("Sorry, this item is not available or has already been bought. Put !shop to see all available items");
		}
	}
}