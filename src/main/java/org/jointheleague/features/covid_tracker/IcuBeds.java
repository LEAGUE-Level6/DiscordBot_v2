
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class IcuBeds {

    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("currentUsageTotal")
    @Expose
    private Integer currentUsageTotal;
    @SerializedName("currentUsageCovid")
    @Expose
    private Integer currentUsageCovid;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentUsageTotal() {
        return currentUsageTotal;
    }

    public void setCurrentUsageTotal(Integer currentUsageTotal) {
        this.currentUsageTotal = currentUsageTotal;
    }

    public Integer getCurrentUsageCovid() {
        return currentUsageCovid;
    }

    public void setCurrentUsageCovid(Integer currentUsageCovid) {
        this.currentUsageCovid = currentUsageCovid;
    }

}
