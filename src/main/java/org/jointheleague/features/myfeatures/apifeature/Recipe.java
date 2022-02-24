package org.jointheleague.features.myfeatures.apifeature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("calories")
    @Expose
    private int calories;
    @SerializedName("carbs")
    @Expose
    private String carbs;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("protein")
    @Expose
    private String protein;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image= image;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
