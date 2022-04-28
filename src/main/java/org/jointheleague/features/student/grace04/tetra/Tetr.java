package org.jointheleague.features.student.grace04.tetra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tetr {
    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
