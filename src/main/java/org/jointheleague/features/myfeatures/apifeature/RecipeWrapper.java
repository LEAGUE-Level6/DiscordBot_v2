package org.jointheleague.features.myfeatures.apifeature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;

import java.util.List;

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

