package org.jointheleague.features.student.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;

import java.util.List;

public class AuctionDataWrapper {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("totalAuctions")
    @Expose
    private Integer totalAuctions;
    @SerializedName("auctions")
    @Expose
    private SkyblockAuction[] auctions;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("lastUpdated")
    @Expose
    private long lastUpdated;


    public Boolean getStatus() {
        return status;
    }

    public Integer getPages() { return totalPages; }

    public Integer getTotalAuctions() { return totalAuctions; }

    public SkyblockAuction[] getAuctions() {
        return auctions;
    }



}
