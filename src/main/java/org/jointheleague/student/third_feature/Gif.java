
package org.jointheleague.student.third_feature;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Gif {

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("dims")
    @Expose
    private List<Integer> dims = null;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getDims() {
        return dims;
    }

    public void setDims(List<Integer> dims) {
        this.dims = dims;
    }

}
