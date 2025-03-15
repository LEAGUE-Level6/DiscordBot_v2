package org.jointheleague.features.student.third_feature;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeWrapper {
		@SerializedName("recipes")
		@Expose
	    private List<Recipe> recipes;  

	    public List<Recipe> getRecipes() {
	        return recipes;
	    }

	    public void setRecipes(List<Recipe> recipes) {
	        this.recipes = recipes;
	    }
	}


