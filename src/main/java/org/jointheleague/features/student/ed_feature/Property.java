package org.jointheleague.features.student.ed_feature;

public class Property {
    String name;
    int initialCost;
    int houseCost;
    int rent;
    int space;

    public Property (String name, int initialCost, int houseCost, int rent,int space ){
        this.name = name;
        this.initialCost = initialCost;
        this.houseCost = houseCost;
        this.rent = rent;
        this.space = space;
    }

    public String getName(){return name;}
    public int getCost(){return initialCost;}
    public int getHouse(){return houseCost;}
    public int getRent(){return rent;}
    public int getSpace(){return space;}
}
