package org.jointheleague.features.student.third_feature;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeWrapper {
		@SerializedName("results")
		@Expose
	    private List<Recipe> results;  

	    public List<Recipe> getRecipes() {
	        return results;
	    }

	    public void setRecipes(List<Recipe> results) {
	        this.results = results;
	    }
	}


