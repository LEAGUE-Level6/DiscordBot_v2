package org.jointheleague.features.student.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreDataWrapper {
    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean getSuccess(){ return success; }
}
