
package org.jointheleague.student.third_feature;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GifMaster {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("next")
    @Expose
    private String next;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
