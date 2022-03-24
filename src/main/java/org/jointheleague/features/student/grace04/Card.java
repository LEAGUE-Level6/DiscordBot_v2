package org.jointheleague.features.student.grace04;

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
}
