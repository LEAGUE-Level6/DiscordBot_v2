package org.jointheleague.features.student.ed_feature;

import java.util.ArrayList;

public class Player {
    ArrayList<Property> properties = new ArrayList<Property>();
    String name;
    int cash;
    public Player(String name){
        this.name = name;
        cash = 1500;
    }

    public int getCash() {
        return cash;
    }
    public void changeCash(int newCash){
        cash += newCash;
    }
    public String getProps(){
        String propertoes = "";
        for (int i = 0; i < properties.size();i++){
            propertoes = propertoes + properties.get(i).getName() +", ";
        }
        return propertoes;
    }
}
