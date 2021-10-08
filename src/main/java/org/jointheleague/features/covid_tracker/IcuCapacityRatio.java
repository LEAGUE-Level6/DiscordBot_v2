
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class IcuCapacityRatio {

    @SerializedName("sources")
    @Expose
    private List<Source__18> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__18> anomalies = null;

    public List<Source__18> getSources() {
        return sources;
    }

    public void setSources(List<Source__18> sources) {
        this.sources = sources;
    }

    public List<Anomaly__18> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__18> anomalies) {
        this.anomalies = anomalies;
    }

}
