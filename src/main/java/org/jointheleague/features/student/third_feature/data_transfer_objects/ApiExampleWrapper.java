package org.jointheleague.features.student.third_feature.data_transfer_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiExampleWrapper {

    @SerializedName("data")
    @Expose
    private List<String> text = null;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

}
