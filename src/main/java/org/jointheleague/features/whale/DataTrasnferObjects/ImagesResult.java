
package org.jointheleague.features.whale.DataTrasnferObjects;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Generated("jsonschema2pojo")
public class ImagesResult {

    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("related_content_id")
    @Expose
    private String relatedContentId;
    @SerializedName("serpapi_related_content_link")
    @Expose
    private String serpapiRelatedContentLink;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("source_logo")
    @Expose
    private String sourceLogo;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("original_width")
    @Expose
    private Integer originalWidth;
    @SerializedName("original_height")
    @Expose
    private Integer originalHeight;
    @SerializedName("is_product")
    @Expose
    private Boolean isProduct;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRelatedContentId() {
        return relatedContentId;
    }

    public void setRelatedContentId(String relatedContentId) {
        this.relatedContentId = relatedContentId;
    }

    public String getSerpapiRelatedContentLink() {
        return serpapiRelatedContentLink;
    }

    public void setSerpapiRelatedContentLink(String serpapiRelatedContentLink) {
        this.serpapiRelatedContentLink = serpapiRelatedContentLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(Integer originalWidth) {
        this.originalWidth = originalWidth;
    }

    public Integer getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(Integer originalHeight) {
        this.originalHeight = originalHeight;
    }

    public Boolean getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(Boolean isProduct) {
        this.isProduct = isProduct;
    }

}
