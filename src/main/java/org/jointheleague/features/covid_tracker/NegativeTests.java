
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NegativeTests {

    @SerializedName("sources")
    @Expose
    private List<Source__3> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__3> anomalies = null;

    public List<Source__3> getSources() {
        return sources;
    }

    public void setSources(List<Source__3> sources) {
        this.sources = sources;
    }

    public List<Anomaly__3> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__3> anomalies) {
        this.anomalies = anomalies;
    }

}
