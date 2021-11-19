
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TestPositivityRatio {

    @SerializedName("sources")
    @Expose
    private List<Source__13> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__13> anomalies = null;

    public List<Source__13> getSources() {
        return sources;
    }

    public void setSources(List<Source__13> sources) {
        this.sources = sources;
    }

    public List<Anomaly__13> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__13> anomalies) {
        this.anomalies = anomalies;
    }

}
