package org.jointheleague.features.student.grace04;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hearthstone {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("faction")
    @Expose
    private String faction;
    @SerializedName("rarity")
    @Expose
    private String rarity;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("cost")
    @Expose
    private int cost;
    @SerializedName("attack")
    @Expose
    private int attack;
    @SerializedName("health")
    @Expose
    private int health;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("flavor")
    @Expose
    private String flavor;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getFaction() {
        return faction;
    }
    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity= rarity;
    }

    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }

    public int getCost(){
        return cost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }

    public int getAttack(){
        return attack;
    }
    public void setAttack(int attack){
        this.attack = attack;
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}