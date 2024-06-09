package org.jointheleague.features.student.first_feature;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunWrapper {

    @SerializedName("pun")
    @Expose
    private String pun;

    public String getPun()
    {
        return pun;
    }

    public void setPun(String pun)
    {
        this.pun = pun;
    }

    public String toString()
    {
        return this.pun;
    }

}