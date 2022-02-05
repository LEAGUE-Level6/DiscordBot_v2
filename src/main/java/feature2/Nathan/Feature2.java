package feature2.Nathan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature2 extends Feature {

    public final String COMMAND = "!animal";
    Random r=new Random();
    int animalNumber;
 
    public Feature2(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Give a brief description of your feature here, including how the user interacts with it"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
            //respond to message here
        	//r.nextInt(10);
        	ArrayList<Integer> animalNumbers=new ArrayList<Integer>();
        	for(int i=0; i<10; i++) {
        		animalNumber=r.nextInt(10);
        		animalNumbers.add(animalNumber);
        	}
        	
        	for(int j=0; j<10; j++) {
        		if (messageContent.startsWith(COMMAND)) {
        			
        	if(animalNumbers.get(j)==0) {
            event.getChannel().sendMessage("Long legs, short bodies, bony horns");
            String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
            if(guessMessage=="giraffe" || guessMessage=="Giraffe") {
                event.getChannel().sendMessage("Correct!");     
            }
        	}
        	
        	if(animalNumbers.get(j)==1) {
        		//monkey
        		event.getChannel().sendMessage("forward facing eyes, grasping hands, nails, large brains");
        	}
        	if(animalNumbers.get(j)==2) {
        		//alligator
        		event.getChannel().sendMessage("long body, thick scales, short legs");
        	}
        	if(animalNumbers.get(j)==3) {
        		//panda
        		event.getChannel().sendMessage("black fur, black ears, white head");
        	}
        	if(animalNumbers.get(j)==4) {
        		//elephant
        		event.getChannel().sendMessage("big head, wide ears, think skin");
        	}
        	if(animalNumbers.get(j)==5) {
        		//whale
        		event.getChannel().sendMessage("long body, blue-gray colored skin");
        	}
        	if(animalNumbers.get(j)==6) {
        		//tiger
        		event.getChannel().sendMessage("powerful carnivore, sharp teeth, agile body");
        	}
        	if(animalNumbers.get(j)==7) {
        		//llama
        		event.getChannel().sendMessage("long neck, thick fur, split upper lip");
        	}
        	if(animalNumbers.get(j)==8) {
        		//kangaroo
        		event.getChannel().sendMessage("muscular tail, large feet, long pointed ears");
        	}
        	if(animalNumbers.get(j)==9) {
        		//bear
        		event.getChannel().sendMessage("large bodies, stocky legs, short tail, shaggy hair");
        	}
        }
    }
    }

}