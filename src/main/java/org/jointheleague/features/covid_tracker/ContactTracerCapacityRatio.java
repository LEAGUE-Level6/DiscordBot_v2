
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ContactTracerCapacityRatio {

    @SerializedName("sources")
    @Expose
    private List<Source__15> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__15> anomalies = null;

    public List<Source__15> getSources() {
        return sources;
    }

    public void setSources(List<Source__15> sources) {
        this.sources = sources;
    }

    public List<Anomaly__15> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__15> anomalies) {
        this.anomalies = anomalies;
    }

}
