package org.jointheleague.features.student.grace04.hearthstone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("cardID")
    @Expose
    private String cardID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cardSet")
    @Expose
    private String cardSet;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("faction")
    @Expose
    private String faction;

    @SerializedName("rarity")
    @Expose
    private String rarity;

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

    @SerializedName("artist")
    @Expose
    private String artist;

    @SerializedName("collectible")
    @Expose
    private boolean collectible;

    @SerializedName("elite")
    @Expose
    private boolean elite;

    @SerializedName("race")
    @Expose
    private String race;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("imgGold")
    @Expose
    private String imgGold;

    @SerializedName("locale")
    @Expose
    private String locale;

    public String getCardID() {
        return cardID;
    }
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }
    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
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

    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isCollectible() {
        return collectible;
    }
    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public boolean isElite() {
        return elite;
    }
    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getImgGold() {
        return imgGold;
    }
    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
