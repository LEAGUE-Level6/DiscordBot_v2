
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class IcuBeds__1 {

    @SerializedName("sources")
    @Expose
    private List<Source__6> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__6> anomalies = null;

    public List<Source__6> getSources() {
        return sources;
    }

    public void setSources(List<Source__6> sources) {
        this.sources = sources;
    }

    public List<Anomaly__6> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__6> anomalies) {
        this.anomalies = anomalies;
    }

}
