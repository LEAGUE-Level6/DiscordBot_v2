package org.jointheleague.features.student.grace04.tetra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TetrWrapper {
    @SerializedName("data?")
    @Expose
    private Tetr data;

    public Tetr getData() {
        return data;
    }
    public void setData(Tetr data) {
        this.data = data;
    }
}