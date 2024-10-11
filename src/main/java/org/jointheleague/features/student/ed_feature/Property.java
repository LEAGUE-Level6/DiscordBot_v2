package org.jointheleague.features.student.ed_feature;

import java.security.acl.Owner;

public class Property extends BoardSpace {

    private int initialCost;
    private int houseCost;
    private int rent;
    private int houseNum;
    private Player owner;


    public Property (String name, int initialCost, int houseCost, int rent){
        super(name);
        this.initialCost = initialCost;
        this.houseCost = houseCost;
        this.rent = rent;
        houseNum = 0;
        owner = new Player(null);
    }

    public String getName(){return super.getDesc();}
    public int getCost(){return initialCost;}
    public int getHouse(){return houseCost;}
    public void buyHouse(){houseNum++;}
    public int getRent(){return rent;}

    public Player getOwner(){return owner;}
    public void setOwner(Player owner){this.owner = owner;}
}
