
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
	String messageContent = "";
	MessageCreateEvent event;
	int pepperoniPizza = 0;
	int ramen = 0;
	int donut = 0;
	int anchove = 0;
	int totalResponses = ramen + pepperoniPizza + donut + anchove;
	
	int questionNum=0;
	long botID= 956022338510536814L;

	public BuzzfeedQuiz(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "");
	}

	@Override
	public void handle(MessageCreateEvent e) {
		event = e;
		messageContent = event.getMessageContent();

//while(totalResponses<5){
		if (event.getMessageAuthor().getId()!=botID) {
		if (messageContent.equals(COMMAND)) {
			event.getChannel().sendMessage(
					"Figure out what food represents you by filling out this quiz! \n Type your responses as you see them in the question but LOWER CASE.\n What do you like to do during your free time? \n 1. Sleep \n 2. Sky dive \n 3. Read a book \n 4. Go to the beach");
		}
		// question 1
		
		else if (messageContent.contains("sleep")) {
			donut++;
			// String donutString=donut+"";
			// event.getChannel().sendMessage(donutString);
			event.getChannel().sendMessage(
					"If you were stuck on an island, who would you want to be stuck with? \n 1. Mom \n 2. Dad \n 3. Best Friend \n 4. Sibling");

		} else if (messageContent.contains("sky dive")) {
			ramen++;
			event.getChannel().sendMessage(
					"If you were stuck on an island, who would you want to be stuck with? \n 1. Mom \n 2. Dad \n 3. Best Friend \n 4. Sibling");

		} else if (messageContent.contains("read a book")) {
			anchove++;
			event.getChannel().sendMessage(
					"If you were stuck on an island, who would you want to be stuck with? \n 1. Mom \n 2. Dad \n 3. Best Friend \n 4. Sibling");

		} else if (messageContent.contains("go to the beach")) {
			pepperoniPizza++;
			event.getChannel().sendMessage(
					"If you were stuck on an island, who would you want to be stuck with? \n 1. Mom \n 2. Dad \n 3. Best Friend \n 4. Sibling");

		}
		// question 2
		else if (messageContent.contains("mom")) {
			pepperoniPizza++;
			event.getChannel()
					.sendMessage("Favorite subject in school: \n 1. Math \n 2. Science \n 3. English \n 4. History");
		} else if (messageContent.contains("dad")) {
			ramen++;
			event.getChannel()
					.sendMessage("Favorite subject in school: \n 1. Math \n 2. Science \n 3. English \n 4. History");

		} else if (messageContent.contains("best friend")) {
			donut++;
			event.getChannel()
					.sendMessage("Favorite subject in school: \n 1. Math \n 2. Science \n 3. English \n 4. History");

		} else if (messageContent.contains("sibling")) {
			anchove++;
			event.getChannel()
					.sendMessage("Favorite subject in school: \n 1. Math \n 2. Science \n 3. English \n 4. History");

		}
		// question 3
		else if (messageContent.contains("math")) {
			donut++;
			event.getChannel()
					.sendMessage("Favorite sport: \n 1. Soccer \n 2. Volleyball \n 3. Field Hockey \n 4. Surfing");
		} else if (messageContent.contains("science")) {
			ramen++;
			event.getChannel()
					.sendMessage("Favorite sport: \n 1. Soccer \n 2. Volleyball \n 3. Field Hockey \n 4. Surfing");

		} else if (messageContent.contains("english")) {
			pepperoniPizza++;
			event.getChannel()
					.sendMessage("Favorite sport: \n 1. Soccer \n 2. Volleyball \n 3. Field Hockey \n 4. Surfing");

		} else if (messageContent.contains("history")) {
			anchove++;
			event.getChannel()
					.sendMessage("Favorite sport: \n 1. Soccer \n 2. Volleyball \n 3. Field Hockey \n 4. Surfing");

		}
		// question 4
		else if (messageContent.contains("soccer")) {
			donut++;
			event.getChannel()
					.sendMessage("Dream Vacation spot \n 1. Italy \n 2. Bora Bora \n 3. Tanzania \n 4. Palm Springs");
		} else if (messageContent.contains("volleyball")) {
			ramen++;
			event.getChannel()
					.sendMessage("Dream Vacation spot \n 1. Italy \n 2. Bora Bora \n 3. Tanzania \n 4. Palm Springs");

		} else if (messageContent.contains("field hockey")) {
			anchove++;
			event.getChannel()
					.sendMessage("Dream Vacation spot \n 1. Italy \n 2. Bora Bora \n 3. Tanzania \n 4. Palm Springs");

		} else if (messageContent.contains("surfing")) {
			pepperoniPizza++;
			event.getChannel()
					.sendMessage("Dream Vacation spot \n 1. Italy \n 2. Bora Bora \n 3. Tanzania \n 4. Palm Springs");

		}
		// question 5
		else if (messageContent.contains("italy")) {
			pepperoniPizza++;
			winner();
		} else if (messageContent.contains("bora bora")) {
			ramen++;
			winner();
		} else if (messageContent.contains("tanzania")) {
			donut++;
			winner();
		} else if (messageContent.contains("palm springs")) {
			anchove++;
			winner();
		}
	}
}
	public void winner() {
		if(pepperoniPizza>ramen && pepperoniPizza>donut && pepperoniPizza>anchove) {
			event.getChannel().sendMessage("Congrats you are a pepperoni pizza!!!");
		}
		else if(donut>ramen && donut>pepperoniPizza && donut>anchove) {
			event.getChannel().sendMessage("Congrats you are a donut!!!");
		}
		else if(ramen>pepperoniPizza && ramen>donut && ramen>anchove) {
			event.getChannel().sendMessage("Congrats you are ramen!!!");
		}
		else if(anchove>ramen && anchove>donut && anchove>pepperoniPizza) {
			event.getChannel().sendMessage("Ew you are anchoves...");
		}
	}

}
