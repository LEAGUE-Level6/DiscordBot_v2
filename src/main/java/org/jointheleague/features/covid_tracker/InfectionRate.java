
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class InfectionRate {

    @SerializedName("sources")
    @Expose
    private List<Source__16> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__16> anomalies = null;

    public List<Source__16> getSources() {
        return sources;
    }

    public void setSources(List<Source__16> sources) {
        this.sources = sources;
    }

    public List<Anomaly__16> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__16> anomalies) {
        this.anomalies = anomalies;
    }

}
