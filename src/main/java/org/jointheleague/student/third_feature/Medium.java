
package org.jointheleague.student.third_feature;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Medium {

    @SerializedName("gif")
    @Expose
    private Gif gif;
    @SerializedName("mediumgif")

    public Gif getGif() {
        return gif;
    }

    public void setGif(Gif gif) {
        this.gif = gif;
    }

}
