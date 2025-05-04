package org.jointheleague.features.student.third_feature;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeWrapper {
		@SerializedName("results")
	    private List<Recipe> recipes = new ArrayList();  

	    public List<Recipe> getRecipes() {
	        return recipes;
	    }
	    
	    public String toString() {
	    	return recipes.size() +" Size ";
	    }

	    public void setRecipes(List<Recipe> results) {
	        this.recipes = results;
	    }
	}


