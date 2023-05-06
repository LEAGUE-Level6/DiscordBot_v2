package org.jointheleague.features.student.blackjack_wrapper;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
public class BlackjackWrapper {

    @JsonProperty("success")
    @Expose
    private Boolean success;
    @JsonProperty("deck_id")
    @Expose
    private String deckId;
    @JsonProperty("shuffled")
    @Expose
    private Boolean shuffled;
    @JsonProperty("remaining")
    @Expose
    private Integer remaining;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public Boolean getShuffled() {
        return shuffled;
    }

    public void setShuffled(Boolean shuffled) {
        this.shuffled = shuffled;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

}