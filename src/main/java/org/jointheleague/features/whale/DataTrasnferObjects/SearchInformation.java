
package org.jointheleague.features.whale.DataTrasnferObjects;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SearchInformation {

    @SerializedName("image_results_state")
    @Expose
    private String imageResultsState;

    public String getImageResultsState() {
        return imageResultsState;
    }

    public void setImageResultsState(String imageResultsState) {
        this.imageResultsState = imageResultsState;
    }

}
