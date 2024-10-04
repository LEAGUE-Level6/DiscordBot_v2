package org.jointheleague.features.student.ed_feature;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EdMonopoly extends Feature {
  public final String command = "!playMonopoly";
  private String leader = "";
  private int playCount = 0;
  HashMap<String, Player> players;
  boolean stillRecruiting = true;
  boolean playing = false;
  public EdMonopoly(String channelName) {
        super(channelName);
        players = new HashMap<String, Player>();
    }


    @Override
    public void handle(ReceivedMessage event) {
        String received = event.getMessageContent();
        if(received.equals(command)) {
            players.put(event.getAuthor().getName(), new Player(event.getAuthor()));
            leader = event.getAuthor().getName();
            playCount++;
            event.sendResponse("Game starting! React to this message with ✅ to begin.");
        }
        if(received.equals("!startGame")){
            stillRecruiting = false;
            playing = true;
            playGame(event);
        }
        if(received.equals("getMyMoney")){
            assert players.get(event.getAuthor().getName()) != null;
            event.sendResponse("" + players.get(event.getAuthor().getName()).getCash());
        }
    }
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
      String react = event.getReaction().getEmoji().getName();
      User playName = event.getUser();

      if(react.equals("✅") && stillRecruiting && playCount <= 8){
          assert playName != null;
          players.put(playName.getName(),new Player(playName));
            playCount++;
      }
    }

    private void playGame(ReceivedMessage event){
      String[] order = players.keySet().toArray(new String[players.size()]);
      while(playing){

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
}
