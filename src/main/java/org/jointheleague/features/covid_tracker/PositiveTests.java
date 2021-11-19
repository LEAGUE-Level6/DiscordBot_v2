
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PositiveTests {

    @SerializedName("sources")
    @Expose
    private List<Source__2> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__2> anomalies = null;

    public List<Source__2> getSources() {
        return sources;
    }

    public void setSources(List<Source__2> sources) {
        this.sources = sources;
    }

    public List<Anomaly__2> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__2> anomalies) {
        this.anomalies = anomalies;
    }

}
