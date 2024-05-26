package org.jointheleague.features.student.first_feature;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.util.*;

public class capitalsGame extends FeatureTemplate {

    public final String COMMAND = "!capitalsGame";
    private final Random random = new Random();
    String country;
    String capital;
    int num;
    String [] answers;
    boolean play = false;


    public capitalsGame(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Guess the correct capital of the country!");
    }

    @Override
    public void handle(MessageCreateEvent event){
        MessageAuthor messageAuthor = event.getMessageAuthor();
        String messageContent = event.getMessageContent();




        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            play = true;
            LinkedList<String> countries = new LinkedList<String>();
            Collections.addAll(countries, new String []{"China", "UK", "Belgium", "Norway", "Japan", "Argentina", "Peru", "Mexico", "Greece", "Australia", "Ecuador",
                                                        "Afghanistan", "Qatar", "Egypt", "South Korea", "Angola", "Kenya", "Indonesia", "Ireland", "Italy", "Germany",
                                                        "Zimbabwe", "Uruguay", "Syria", "Paraguay", "Hungary", "Austria", "Azerbaijan", "Jamaica"});
            LinkedList<String> capitals = new LinkedList<String>();
            Collections.addAll(capitals, new String[]{"Beijing", "London", "Brussels", "Oslo", "Tokyo", "Buenos Aires", "Lima", "Mexico City", "Athens", "Canberra",
                                                      "Quito", "Kabul", "Doha", "Cairo", "Seoul", "Luanda", "Nairobi", "Jakarta", "Dublin", "Rome", "Berlin",
                                                      "Harare", "Montevideo", "Damascus", "Asuncion", "Budapest", "Vienna", "Baku", "Kingston"});

             num = random.nextInt(capitals.size());

             country = countries.remove(num);
             capital = capitals.remove(num);



          answers = new String [5];

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

            event.getChannel().sendMessage("What is the capital of "+country+"? (Answer with the command followed by 'A', 'B', 'C', or 'D')"+
                    "\nA: "+answers[0]+
                    "\nB: "+answers[1]+
                    "\nC: "+answers[2]+
                    "\nD: "+answers[3]);
        }
        //check a guess
        else if (messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:") && play) {


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
                    play = false;
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is something else! Try again!");
                }
            }
            else if(guess.equals("B")){
                if(answers[1].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                    play = false;
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is something else! Try again!");
                }
            }
            else if(guess.equals("C")){
                if(answers[2].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                    play = false;
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is something else! Try again!");
                }
            }
            else if(guess.equals("D")){
                if(answers[3].equals(capital))
                {
                    event.getChannel().sendMessage("Yes! The capital of "+country+ " is "+capital+ "! Well done!");
                    play = false;
                }else{
                    event.getChannel().sendMessage("Nope! The capital of "+country+ " is something else! Try again!");
                }
            }
            else
            {
                event.getChannel().sendMessage("Please enter a valid command. ");
            }
            event.getChannel().sendMessage(guess + " ");

        }
    }
}
