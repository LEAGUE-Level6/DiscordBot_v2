
package org.jointheleague.student.third_feature;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content_description")
    @Expose
    private String contentDescription;
    @SerializedName("content_rating")
    @Expose
    private String contentRating;
    @SerializedName("h1_title")
    @Expose
    private String h1Title;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    @SerializedName("bg_color")
    @Expose
    private String bgColor;
    @SerializedName("created")
    @Expose
    private Double created;
    @SerializedName("itemurl")
    @Expose
    private String itemurl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("flags")
    @Expose
    private List<Object> flags = null;
    @SerializedName("shares")
    @Expose
    private Integer shares;
    @SerializedName("hasaudio")
    @Expose
    private Boolean hasaudio;
    @SerializedName("hascaption")
    @Expose
    private Boolean hascaption;
    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("composite")
    @Expose
    private Object composite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getH1Title() {
        return h1Title;
    }

    public void setH1Title(String h1Title) {
        this.h1Title = h1Title;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Double getCreated() {
        return created;
    }

    public void setCreated(Double created) {
        this.created = created;
    }

    public String getItemurl() {
        return itemurl;
    }

    public void setItemurl(String itemurl) {
        this.itemurl = itemurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Object> getFlags() {
        return flags;
    }

    public void setFlags(List<Object> flags) {
        this.flags = flags;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Boolean getHasaudio() {
        return hasaudio;
    }

    public void setHasaudio(Boolean hasaudio) {
        this.hasaudio = hasaudio;
    }

    public Boolean getHascaption() {
        return hascaption;
    }

    public void setHascaption(Boolean hascaption) {
        this.hascaption = hascaption;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Object getComposite() {
        return composite;
    }

    public void setComposite(Object composite) {
        this.composite = composite;
    }

}
