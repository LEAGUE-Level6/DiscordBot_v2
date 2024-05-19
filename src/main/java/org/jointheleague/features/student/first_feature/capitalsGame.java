package org.jointheleague.features.student.first_feature;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.*;

public class capitalsGame extends FeatureTemplate {

    public final String COMMAND = "!capitalsGame";
    private final Random random = new Random();



    public capitalsGame(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Guess the correct capital of the country!");
    }

    @Override
    public void handle(MessageCreateEvent event){

        String messageContent = event.getMessageContent();

        String country = "";
        String capital = "";
        int num = 0;
        String [] answers = new String [4];

        //start the game with the command
        if (messageContent.equals(COMMAND)) {

            LinkedList<String> countries = new LinkedList<String>();
            Collections.addAll(countries, new String []{"China", "UK", "Belgium", "Norway", "Japan", "Argentina", "Peru", "Mexico"});
            LinkedList<String> capitals = new LinkedList<String>();
            Collections.addAll(capitals, new String[]{"Beijing", "London", "Brussels", "Oslo", "Tokyo", "Buenos Aires", "Lima", "Mexico City"});

             num = random.nextInt(capitals.size());

             country = countries.remove(num);
             capital = capitals.remove(num);



            answers = new String [4];

            int answerIndex = random.nextInt(4);
            answers[answerIndex] = capital;
            for(int i = 0; i < answers.length-1; i++)
            {
                if(answers[i] == null)
                {
                    int newRan = random.nextInt(capitals.size());
                    answers[i] = capitals.remove(newRan);
                }
            }

            event.getChannel().sendMessage("What is the capital of "+country+"? (Answer with 'A', 'B', 'C', or 'D')"+
                    "\nA: "+answers[0]+
                    "\nB: "+answers[1]+
                    "\nC: "+answers[2]+
                    "\nD: "+answers[3]);
        }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")) {
            System.out.println(capital);

            //check if the game has been started
            if(capital.isEmpty()){
                //tell them to start the game first
                event.getChannel().sendMessage("Please start the game first using just the command");
                return;
            }

            //parse the guess from the message
            String guess = messageContent.replaceAll(" ", "").replace(COMMAND, "");

            if(guess.equals("A")) {
                if(answers[0].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is actually "+capital+ "! Better luck next time!");
                }
            }
            else if(guess.equals("B")){
                if(answers[1].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is actually "+capital+ "! Better luck next time!");
                }
            }
            else if(guess.equals("C")){
                if(answers[2].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is actually "+capital+ "! Better luck next time!");
                }
            }
            else if(guess.equals("D")){
                if(answers[3].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is actually "+capital+ "! Better luck next time!");
                }
            }
            else
            {
                event.getChannel().sendMessage("?????");
            }
            event.getChannel().sendMessage(guess + " ");

        }
    }
}
