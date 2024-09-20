package org.jointheleague.features.student.ed_feature;

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
}
