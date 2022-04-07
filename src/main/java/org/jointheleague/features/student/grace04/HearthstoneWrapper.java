package org.jointheleague.features.student.grace04;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HearthstoneWrapper {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("results")
    @Expose
    private Card[] results;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Card[] getResults() {
        return results;
    }

    public void setResults(Card[] results) {
        this.results = results;
    }

}
