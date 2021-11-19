
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class InfectionRateCI90 {

    @SerializedName("sources")
    @Expose
    private List<Source__17> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__17> anomalies = null;

    public List<Source__17> getSources() {
        return sources;
    }

    public void setSources(List<Source__17> sources) {
        this.sources = sources;
    }

    public List<Anomaly__17> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__17> anomalies) {
        this.anomalies = anomalies;
    }

}
