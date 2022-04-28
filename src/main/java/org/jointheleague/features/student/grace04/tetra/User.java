package org.jointheleague.features.student.grace04.tetra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("xp")
    @Expose
    private float xp;
    @SerializedName("league")
    @Expose
    private League league;


    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username= username;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role= role;
    }

    public float getXp(){
        return xp;
    }
    public void setXp(float xp){
        this.xp = xp;
    }

    public League getLeague(){
        return league;
    }
    public void setLeague(League league){
        this.league = league;
    }
}
