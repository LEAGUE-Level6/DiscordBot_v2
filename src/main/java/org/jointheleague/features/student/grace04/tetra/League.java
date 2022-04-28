package org.jointheleague.features.student.grace04.tetra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class League {
    @SerializedName("gamesplayed")
    @Expose
    private int gamesplayed;
    @SerializedName("gameswon")
    @Expose
    private int gameswon;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("standing")
    @Expose
    private int standing;
    @SerializedName("percentile")
    @Expose
    private float percentile;

    public int getGamesplayed() {
        return gamesplayed;
    }
    public void setGamesplayed(int gamesplayed) {
        this.gamesplayed = gamesplayed;
    }

    public int getGameswon() {
        return gameswon;
    }
    public void setGameswon(int gameswon) {
        this.gameswon = gameswon;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String gamesplayed) {
        this.rank = rank;
    }

    public int getStanding() {
        return standing;
    }
    public void setStanding(int standing) {
        this.standing = standing;
    }

    public float getPercentile() {
        return percentile;
    }
    public void setPercentile(float percentile) {
        this.percentile = percentile;
    }
}
