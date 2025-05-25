package org.jointheleague.features.student.third_feature;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private String title;
    private String instructions;

    @SerializedName("extendedIngredients")
    private List<Ingredient> ingredients;

    
    public int getId() { 
    	return id; 
    	}

    public void setId(int id) { 
    	this.id = id; 
    	}

    public String getTitle() {
    	return title; 
    	}

    public void setTitle(String title) {
    	this.title = title;
    	}

    public String getInstructions() {
    	return instructions;
    	}

    public void setInstructions(String instructions) {
    	this.instructions = instructions;
    	}

    public List<Ingredient> getIngredients() {
    	return ingredients;
    	}

    public void setIngredients(List<Ingredient> ingredients) {
    	this.ingredients = ingredients;
    	}
    public String getRecipe () {
    	return title + ingredients + instructions;
    }
}