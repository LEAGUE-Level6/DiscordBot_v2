package org.jointheleague.features.student.grace04.tetra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TetrWrapper {
    @SerializedName("data?")
    @Expose
    private Tetr data;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("error?")
    @Expose
    private String error;

    public Tetr getData() {
        return data;
    }
    public void setData(Tetr data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}