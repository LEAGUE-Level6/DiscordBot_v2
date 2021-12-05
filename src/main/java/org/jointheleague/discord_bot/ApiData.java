package org.jointheleague.discord_bot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiData {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("download_url")
    @Expose
    private String download_url;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;

    public String getUrl() {
        return url;
    }
    public String getDownload_url() {
        return download_url;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }


}
