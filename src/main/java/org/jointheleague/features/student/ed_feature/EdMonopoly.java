package org.jointheleague.features.student.ed_feature;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class EdMonopoly extends Feature {
  public final String command = "!playMonopoly";
  private String leader = "";
  private int playCount = 0;
  PlayerManager playerManage;
  BoardSpace[] locations;
  boolean stillRecruiting = true;
  boolean playing = false;
  public EdMonopoly(String channelName) {
        super(channelName);
        playerManage = new PlayerManager();

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
        switch(received){
        case command:

            playerManage.addPlayer(new Player(event.getAuthor()));
            leader = event.getAuthor().getName();
            playCount++;
            event.sendResponse("Game starting! Type !join to begin.");
        break;
            case"!startGame":
            stillRecruiting = false;
            playing = true;
            playGame(event);
        break;
            case "!getMyMoney":
            event.sendResponse("" + playerManage.getCurr().getCash());
        break;
        case "!join":
                if( stillRecruiting && playCount <= 8){
            playerManage.addPlayer(new Player(event.getAuthor()));
            playCount++;
        }
        break;
        case "!buy":
            buyTime = false;
            assert buyTime;
            ((Property)locations[propToFind]).setOwner(playerManage.getCurr());
            buyTime = false;
        break;
        case "!end":
            playerManage.advanceTurn();
        break;
        }

    }
    int propToFind = 0;
    boolean buyTime = false;
//    @Override
//    public void onMessageReactionAdd(MessageReactionAddEvent event){
//      String react = event.getReaction().getEmoji().getName();
//      User playName = event.getUser();
//        System.out.println(react);
//      if(react.equals("✅") && stillRecruiting && playCount <= 8){
//          assert playName != null;
//          playerManage.addPlayer(new Player(playName));
//            playCount++;
//      }
//      if(react.equals("\uD83E\uDD11") && buyTime){
//          buyTime = false;
//          assert playName != null;
//          ((Property)locations[propToFind]).setOwner(playerManage.getCurr());
//      }
//    }

    private void playGame(ReceivedMessage event){
      Player activePlayer = null;
      int rollVal = 0;
      while(playing) {
          if (!buyTime) {
              activePlayer = playerManage.getCurr();
              rollVal = rollDie();
              activePlayer.changeLocation(rollVal);
              event.sendResponse(rollVal + "rolled, you landed on" + locations[activePlayer.getLocation()].getDesc()+ "\nTo end your turn, type !end.");
              if (((Property) locations[activePlayer.getLocation()]).getOwner() != null) {
                  event.sendResponse("Nobody owns this property! Reply with !buy to buy this property for " + ((Property) locations[activePlayer.getLocation()]).getCost());
                  propToFind = activePlayer.getLocation();
                  buyTime = true;
              } else if (locations[activePlayer.getLocation()].getDesc().contains("Chance")) {
                  event.sendResponse("Chance Unimplemented");
              } else if (locations[activePlayer.getLocation()].getDesc().contains("Community Chest")) {
                  event.sendResponse("Community Chest Unimplemented");
              } else if (((Property) locations[activePlayer.getLocation()]).getOwner() != activePlayer) {
                  activePlayer.changeCash(activePlayer.getCash() - ((Property) locations[activePlayer.getLocation()]).getCost());
                  event.sendResponse("Uh Oh! You landed on " + ((Property) locations[activePlayer.getLocation()]).getOwner() + "'s property! You lose $" + ((Property) locations[activePlayer.getLocation()]).getCost());
              }
              //((Property)locations[activePlayer.getLocation() + rollVal]).getHouse();

          }
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

class PlayerManager {
    List<Player> players = new ArrayList<Player>();
    int turnTracker = 0;

    void addPlayer(Player newP){players.add(newP);}
    Player getCurr(){return players.get(turnTracker);}

    void advanceTurn(){turnTracker = turnTracker == players.size()?0:turnTracker+1;}
}
