package org.jointheleague.features.student.grace04.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeWrapper {
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("results")
    @Expose
    private Recipe[] results;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Recipe[] getResults() {
        return results;
    }

    public void setResults(Recipe[] results) {
        this.results = results;
    }
}
