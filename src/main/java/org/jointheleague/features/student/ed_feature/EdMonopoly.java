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
  BoardSpace[] locations;
  boolean stillRecruiting = true;
  boolean playing = false;
  public EdMonopoly(String channelName) {
        super(channelName);
        players = new HashMap<String, Player>();

        locations = new BoardSpace[]{
                new BoardSpace("GO"),
                new Property("Mediterranean Avenue", 60, 40, 20),
                new BoardSpace("Community Chest 1"),
                new Property("Baltic Avenue", 60, 40,20),
                new BoardSpace("Luxury Tax 1"),
                new Property( "Reading Railroad", 200, 0,  60),
                new Property("Oriental Avenue", 100, 80, 30),
                new Property("Vermont Avenue", 100, 80, 30),
                new BoardSpace("Chance 1"),
                new Property( "Connecticut Avenue", 120, 100, 40),
                new BoardSpace("Jail"),
                new Property("Saint Charles Place", 140, 120, 40),
                new Property("Electric Company", 150, 0,50),
                new Property("States Avenue", 140, 120, 40),
                new Property("Virginia Avenue", 140, 120, 40),
                new Property("Pennsylvania Railroad", 200, 0, 60),
                new Property("Saint James Place", 180, 160, 60),
                new BoardSpace("Community Chest 2"),
                new Property("Tennessee Avenue", 180, 160, 60),
                new Property("New York Avenue", 200, 180, 60),
                new BoardSpace("Free Parking"),
                new Property("Kentucky Avenue", 220, 200, 70),
                new BoardSpace("Chance 2"),
                new Property("Indiana Avenue", 220, 200, 70),
                new Property("Illinois Avenue", 240, 220, 80),
                new Property("B. & O. Railroad", 200, 0, 60),
                new Property("Atlantic Avenue", 260, 240, 80),
                new Property("Vermont Avenue", 260, 240, 80),
                new Property("Water Works", 150, 0, 50),
                new Property("Marvin Gardens", 280, 240, 90),
                new BoardSpace("Go To Jail"),
                new Property("Pacific Avenue", 300, 280, 100),
                new Property("North Carolina Avenue", 300, 280, 100),
                new BoardSpace("Community Chest 3"),
                new Property("Pennsylvania Avenue", 320, 300, 100),
                new Property("Short Line", 200, 0, 60),
                new BoardSpace("Chance 3"),
                new Property("Park Place", 350, 320, 110),
                new BoardSpace("Luxury Tax 2"),
                new Property("Boardwalk", 400, 380, 130)
        };
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
    int propToFind = 0;
    boolean buyTime = false;
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
      String react = event.getReaction().getEmoji().getName();
      User playName = event.getUser();
      if(react.equals("✅") && stillRecruiting && playCount <= 8){
          assert playName != null;
          players.put(playName.getName(),new Player(playName));
            playCount++;
      }
      if(react.equals(":money_mouth") && buyTime){
          buyTime = false;
          assert playName != null;
          ((Property)locations[propToFind]).setOwner(players.get(playName.getName()));
      }
    }

    private void playGame(ReceivedMessage event){
      String[] order = players.keySet().toArray(new String[players.size()]);
      int playerTurn = 0;
      Player activePlayer = new Player(null);
      int rollVal = 0;
      while(playing){
        activePlayer = players.get(order[playerTurn]);
        rollVal = rollDie();
        activePlayer.changeLocation(rollVal);
        event.sendResponse(rollVal + "rolled, you landed on" + locations[activePlayer.getLocation()].getDesc());
         if(((Property)locations[activePlayer.getLocation()]).getOwner() != activePlayer){
             event.sendResponse("Nobody owns this property! React with the :money_mouth: emoji to buy this property for " + ((Property)locations[activePlayer.getLocation()]).getCost());
            propToFind = activePlayer.getLocation();
            buyTime = true;
         }
          //((Property)locations[activePlayer.getLocation() + rollVal]).getHouse();
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
