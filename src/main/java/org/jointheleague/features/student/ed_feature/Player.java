package org.jointheleague.features.student.ed_feature;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class Player {
    ArrayList<Property> properties = new ArrayList<Property>();
    private final User user;
    private int cash;
    private int location;
    boolean isInJail = false;
    public Player(User user){
        this.user = user;
        cash = 1500;
        location = 0;
    }

    public int getCash() {
        return cash;
    }
    public void changeCash(int newCash){
        cash += newCash;
    }
    public String getName(User user){return user.getName();}
    public String getProps(){
        String propertoes = "";
        for (int i = 0; i < properties.size();i++){
            propertoes = propertoes + properties.get(i).getName() +", ";
        }
        return propertoes;
    }
    public boolean inJail(){return isInJail;}
    public void sendToJail(){isInJail = true;}

    public int getLocation(){return location;}
    public void changeLocation(int change){location = location + change > 39 ? location + change - 40 : location + change;}
}
