package org.jointheleague.features.examples.third_features.plain_old_java_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreDataWrapper {
    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean getSuccess(){ return success; }
}
