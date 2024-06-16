package org.jointheleague.features.student.first_feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiddleWrapper {

    @SerializedName("riddle")
    @Expose
    private String riddle;
    @SerializedName("answer")
    @Expose
    private String answer;

    public String getRiddle() {
        return riddle;
    }

    public void setRiddle(String riddle) {
        this.riddle = riddle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String toString()
    {
        return "RIDDLE: " + this.riddle + "\n\nANSWER: "+ this.answer;
    }

}