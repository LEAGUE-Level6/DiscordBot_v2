package org.jointheleague.features.Feature_3;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class apod_wrapper {
    @SerializedName("date")
    @Expose
    private String date;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @SerializedName("explanation")
    @Expose
    private String explanation;
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String URL;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
