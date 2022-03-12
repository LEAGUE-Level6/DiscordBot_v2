package feature2.Nathan;

import java.util.ArrayList;
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
	int animalNumber;
	int state=0;
	int animalNumbers = r.nextInt(1);
	String guessMessage1="Long legs, short bodies, bony horns";
	String guessMessage2="Longlegs,shortbodies,bonyhorns";
	// put a variable here that keeps track of the # of times handle is called
	// this will ensure that the code picks the animal message that corresponds with
	// the calling variable's index for the distinct random # arraylist

	public Feature2(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"Give a brief description of your feature here, including how the user interacts with it");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		//whenever I input a message, it should call handle
		String messageContent = event.getMessageContent();
		//if(event.getMessageAuthor().isYourself()){
		//if(event.getMessageAuthor()==this.);	
		if(state==0) {
		if (messageContent.startsWith(COMMAND)) {
			// respond to message here
			// r.nextInt(10);
			if (animalNumbers == 0) {
				event.getChannel().sendMessage("Long legs, short bodies, bony horns");
			}

			/*
			 * else if(animalNumbers==1) { //monkey event.getChannel().
			 * sendMessage("forward facing eyes, grasping hands, nails, large brains"); }
			 * else if(animalNumbers==2) { //alligator
			 * event.getChannel().sendMessage("long body, thick scales, short legs"); } else
			 * { event.getChannel().sendMessage("message is sent"); }
			 * 
			 */

			/*
			 * if(animalNumbers.get(j)==3) { //panda
			 * event.getChannel().sendMessage("black fur, black ears, white head"); }
			 * if(animalNumbers.get(j)==4) { //elephant
			 * event.getChannel().sendMessage("big head, wide ears, think skin"); }
			 * if(animalNumbers.get(j)==5) { //whale
			 * event.getChannel().sendMessage("long body, blue-gray colored skin"); }
			 * if(animalNumbers.get(j)==6) { //tiger
			 * event.getChannel().sendMessage("powerful carnivore, sharp teeth, agile body"
			 * ); } if(animalNumbers.get(j)==7) { //llama
			 * event.getChannel().sendMessage("long neck, thick fur, split upper lip"); }
			 * if(animalNumbers.get(j)==8) { //kangaroo
			 * event.getChannel().sendMessage("muscular tail, large feet, long pointed ears"
			 * ); } if(animalNumbers.get(j)==9) { //bear event.getChannel().
			 * sendMessage("large bodies, stocky legs, short tail, shaggy hair"); }
			 */
			
		}
		}
		
		//String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
		
		if(event.getMessageAuthor().isYourself()){
			if(event.getMessageContent()!="!animal") {
				if(event.getMessageContent()!=guessMessage1) {
					if(event.getMessageContent().length()>1) {
					state+=1;
		if(state==1) {
		if( event.getMessageContent().equals("Giraffe") || event.getMessageContent().equals("giraffe") ) {
			if (animalNumbers == 0) {
				event.getChannel().sendMessage("Correct!");
			} 
		}
		else if(event.getMessageContent().length()>1){
			System.out.println(event.getMessageContent());
				event.getChannel().sendMessage("Sorry, incorrect. The correct answer is a giraffe");
		}
	}
				}
}
}
}
}
}


// Set<Integer> set = new LinkedHashSet<Integer>();
// while (set.size() < 10) {
// set.add(r.nextInt(10));
// }
//

// List<Integer> animalNumbers = new ArrayList<Integer>(set);
