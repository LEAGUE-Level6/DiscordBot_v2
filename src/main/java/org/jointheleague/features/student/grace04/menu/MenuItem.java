package org.jointheleague.features.student.grace04.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItem {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("restaurantChain")
    @Expose
    private String restaurantChain;
    @SerializedName("image")
    @Expose
    private String image;


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getRestaurantChain(){
        return restaurantChain;
    }
    public void setRestaurantChain(String restaurantChain){
        this.restaurantChain = restaurantChain;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image= image;
    }
}
