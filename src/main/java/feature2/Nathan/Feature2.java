package feature2.Nathan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature2 extends Feature {

	public final String COMMAND = "!animal";
	Random r = new Random();
	int animalNumbers;
	int i = 0;
	String response = "message not set";
	String guessMessage = "";
	ArrayList<Integer> values = new ArrayList<Integer>();

	// put a variable here that keeps track of the # of times handle is called
	// this will ensure that the code picks the animal message that corresponds with
	// the calling variable's index for the distinct random # arraylist

	public Feature2(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"When the command !animal is inputed into the bot, a set of clues perating to a specific animal will be revealed and you will guess what animal is being described. To guess type !animal + your guess (ex: !animal dog). Make sure you spell the name of the animal correctly. To play again, simply type in !animal again. Have fun!");
	}

	public void distinctRandom() {
		for (int i = 0; i < 10;) {
			animalNumbers = r.nextInt(10);
			if (!values.contains(animalNumbers)) {
				values.add(animalNumbers);
				i++;
			}
		}
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// whenever I input a message, it should call handle
		String messageContent = event.getMessageContent();
		// if(event.getMessageAuthor().isYourself()){
		// if(event.getMessageAuthor()==this.);

		if (messageContent.equals(COMMAND)) {
			if(i==0) {
			distinctRandom();
			}
			
			if(i>9) {
			i=0;
			values=new ArrayList<Integer>();
			distinctRandom();
			}
			
			// giraffe
			if (values.get(i) == 0) {
				event.getChannel().sendMessage("Long legs, short bodies, bony horns");
			}

			// monkey
			if (values.get(i) == 1) {
				event.getChannel().sendMessage("forward facing eyes, grasping hands, nails, large brains");
			}

			// alligator
			if (values.get(i) == 2) {
				event.getChannel().sendMessage("long body, thick scales, short legs");
			}
			
			//panda
			if(values.get(i) == 3) {
				event.getChannel().sendMessage("black fur, black ears, white head");
			}
			
			//elephant
			if(values.get(i) == 4) {
				event.getChannel().sendMessage("big head, wide ears, think skin");
			}
			
			//whale
			if(values.get(i)==5) {
				event.getChannel().sendMessage("long body, blue-gray colored skin");
			}
			
			//tiger
			if(values.get(i)==6) {
				event.getChannel().sendMessage("powerful carnivore, sharp teeth, agile body");
			}
			
			//llama
			if(values.get(i)==7) {
				event.getChannel().sendMessage("long neck, thick fur, split upper lip");
			}
			
			//kangaroo
			if(values.get(i)==8) {
				event.getChannel().sendMessage("muscular tail, large feet, long pointed ears");
			}
            
			//bear
			if(values.get(i)==9) {
				event.getChannel().sendMessage("large bodies, stocky legs, short tail, shaggy hair");
			}
		}

		else if (messageContent.contains(COMMAND)) {
			String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
			interpretGuess(event, guessMessage);
		}
	}

	public void interpretGuess(MessageCreateEvent event, String guess) {
		if(i>values.size()) {
		return;
		}
		
		if (values.get(i) == 0) {
			if (guess.equalsIgnoreCase("Giraffe")) {
				response = "Correct!";
				i++;
			} else if (guessMessage.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a giraffe";
				i++;
			}
		}

		else if (values.get(i) == 1) {
			if (guess.equalsIgnoreCase("monkey")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a monkey";
				i++;
			}
		}

		else if (values.get(i) == 2) {
			if (guess.equalsIgnoreCase("alligator")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a alligator";
				i++;
			}
		}
		
		else if (values.get(i) == 3) {
			if (guess.equalsIgnoreCase("panda")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a panda";
				i++;
			}
		}
		
		else if (values.get(i) == 4) {
			if (guess.equalsIgnoreCase("elephant")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a elephant";
				i++;
			}
		}
		
		else if (values.get(i) == 5) {
			if (guess.equalsIgnoreCase("whale")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a whale";
				i++;
			}
		}
		
		else if (values.get(i) == 6) {
			if (guess.equalsIgnoreCase("tiger")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a tiger";
				i++;
			}
		}
		
		else if (values.get(i) == 7) {
			if (guess.equalsIgnoreCase("llama")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a llama";
				i++;
			}
		}
		
		else if (values.get(i) == 8) {
			if (guess.equalsIgnoreCase("kangaroo")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a kangaroo";
				i++;
			}
		}
		
		else if (values.get(i) == 9) {
			if (guess.equalsIgnoreCase("bear")) {
				response = "Correct!";
				i++;
			} else if (guess.length() > 1) {
				response = "Sorry, incorrect. The correct answer is a bear";
				i++;
			}
		}
		event.getChannel().sendMessage(response);
	}
}