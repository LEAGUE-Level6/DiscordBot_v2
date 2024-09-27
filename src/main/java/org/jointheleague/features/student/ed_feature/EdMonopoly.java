package org.jointheleague.features.student.ed_feature;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;

import java.util.HashMap;
import java.util.Random;

public class EdMonopoly extends Feature {
  public final String command = "!playMonopoly";
  HashMap<String, Player> players;
  public EdMonopoly(String channelName) {
        super(channelName);
        players = new HashMap<String, Player>();
    }


    @Override
    public void handle(ReceivedMessage event) {
        String recieved = event.getMessageContent();
        if(recieved.equals(command))
            playGame();
    }
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
      String react = event.getReaction().getEmoji().getName();
      String playNum = event.getUserId();


      if(react.equals("✅")){
          System.out.println(playNum);
      }
    }
    Random rand = new Random();
    private int rollDie(){
        int totalRolled = 0;
        int dieOne = 0;
        int dieTwo = 0;
        int rollCount = 1;
        boolean rolling = true;
        while(rolling){
            rolling = false;
            dieOne = rand.nextInt(6)+1;
            dieTwo = rand.nextInt(6)+1;
            totalRolled = dieOne + dieTwo;
            if(dieOne == dieTwo){
                rollCount++;
                rolling = true;
            }
        }
      return totalRolled;
    }
    private void playGame(){
        players = new HashMap<>();
       boolean stillRecruiting = true;

       /*this loop should grab the usernames of each player that reacts with a checkmark to it
       it should also use stillRecruiting to check for if the player that started the game wants to start
       start could be by command or by tracking reactions (reactions will be easier I bet).
        */
        while(stillRecruiting){

            stillRecruiting = false;
        }

    }
}
