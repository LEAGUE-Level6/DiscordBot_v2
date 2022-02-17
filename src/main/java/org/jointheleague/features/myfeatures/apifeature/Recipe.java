package org.jointheleague.features.myfeatures.apifeature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("titles")
    @Expose
    private String titles;
    @SerializedName("calories")
    @Expose
    private Integer calories;
    @SerializedName("carbs")
    @Expose
    private String carbs;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("protein")
    @Expose
    private String protein;


    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
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

}
