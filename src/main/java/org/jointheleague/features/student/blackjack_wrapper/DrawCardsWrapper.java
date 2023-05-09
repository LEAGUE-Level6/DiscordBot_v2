
package org.jointheleague.features.student.blackjack_wrapper;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
public class DrawCardsWrapper {

    @JsonProperty("success")
    @Expose
    private Boolean success;
    @JsonProperty("deck_id")
    @Expose
    private String deckId;
    @JsonProperty("cards")
    @Expose
    private List<Card> cards;
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

}
