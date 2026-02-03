package org.jointheleague.features.examples.third_features.plain_old_java_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Source;

public class SkyblockAuction {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("start")
    @Expose
    private long start;
    @SerializedName("item_name")
    @Expose
    private String item_name;
    @SerializedName("item_lore")
    @Expose
    private String item_lore;
    @SerializedName("extra")
    @Expose
    private String extra;
    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("starting_bid")
    @Expose
    private long starting_bid;
    @SerializedName("item_bytes")
    @Expose
    private Object item_bytes;
    @SerializedName("highest_bid_amount")
    @Expose
    private long highest_bid_amount;
    @SerializedName("bin")
    @Expose
    private Boolean bin;

    public String getId() {
        return id;
    }

    public boolean getBin() { return bin; }

    public long getStart(Source source) {
        return start;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_lore() {
        return item_lore;
    }

    public String getExtra() { return extra; }

    public String getTier(String title) { return tier; }

    public long getStarting_bid() {
        return starting_bid;
    }

    public Object getItem_bytes(String description) {
        return item_bytes;
    }

    public long getHighest_bid_amount() {
        return highest_bid_amount;
    }
}
