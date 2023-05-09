
package org.jointheleague.features.student.blackjack_wrapper;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
public class Card {

    @JsonProperty("code")
    @Expose
    private String code;
    @JsonProperty("image")
    @Expose
    private String image;
    @JsonProperty("images")
    @Expose
    private Images images;
    @JsonProperty("value")
    @Expose
    private String value;
    @JsonProperty("suit")
    @Expose
    private String suit;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

}
