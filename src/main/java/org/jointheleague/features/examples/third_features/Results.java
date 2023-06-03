package org.jointheleague.features.examples.third_features;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Source;

public class Results {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String title) {
        this.imageType = imageType;
    }
}
