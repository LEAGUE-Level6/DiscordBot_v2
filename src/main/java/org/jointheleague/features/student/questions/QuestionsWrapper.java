package org.jointheleague.features.student.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsWrapper {

    @SerializedName("results")
    @Expose
    private List<String> data = null;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
