
package org.jointheleague.features.whale.DataTrasnferObjects;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RelatedSearch {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("serpapi_link")
    @Expose
    private String serpapiLink;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("highlighted_words")
    @Expose
    private List<String> highlightedWords;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSerpapiLink() {
        return serpapiLink;
    }

    public void setSerpapiLink(String serpapiLink) {
        this.serpapiLink = serpapiLink;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getHighlightedWords() {
        return highlightedWords;
    }

    public void setHighlightedWords(List<String> highlightedWords) {
        this.highlightedWords = highlightedWords;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
