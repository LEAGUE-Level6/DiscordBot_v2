package org.jointheleague.features.student.first_feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PunWrapper {

    @SerializedName("data")
    @Expose
    private List<String> data = null;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
