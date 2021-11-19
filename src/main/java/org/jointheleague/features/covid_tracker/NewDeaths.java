
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NewDeaths {

    @SerializedName("sources")
    @Expose
    private List<Source__8> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__8> anomalies = null;

    public List<Source__8> getSources() {
        return sources;
    }

    public void setSources(List<Source__8> sources) {
        this.sources = sources;
    }

    public List<Anomaly__8> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__8> anomalies) {
        this.anomalies = anomalies;
    }

}
