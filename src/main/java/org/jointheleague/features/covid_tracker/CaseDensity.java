
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CaseDensity {

    @SerializedName("sources")
    @Expose
    private List<Source__14> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__14> anomalies = null;

    public List<Source__14> getSources() {
        return sources;
    }

    public void setSources(List<Source__14> sources) {
        this.sources = sources;
    }

    public List<Anomaly__14> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__14> anomalies) {
        this.anomalies = anomalies;
    }

}
