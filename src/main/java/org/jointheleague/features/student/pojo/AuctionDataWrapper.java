package org.jointheleague.features.student.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;

import java.util.List;

public class AuctionDataWrapper {
    @SerializedName("totalAuctions")
    @Expose
    private int totalAuctions;
    @SerializedName("auctions")
    @Expose
    private SkyblockAuction[] auctions;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("lastUpdated")
    @Expose
    private long lastUpdated;
    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean getSuccess(){ return success; }


    public boolean getStatus() {
        return success;
    }

    public Integer getTotalPages() { return totalPages; }

    public Integer getTotalAuctions() { return totalAuctions; }

    public SkyblockAuction[] getAuctions() {
        return auctions;
    }
    public void spoof(){
        success=false;
    }



}
