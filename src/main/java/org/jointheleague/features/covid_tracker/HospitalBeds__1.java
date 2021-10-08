
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class HospitalBeds__1 {

    @SerializedName("sources")
    @Expose
    private List<Source__5> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__5> anomalies = null;

    public List<Source__5> getSources() {
        return sources;
    }

    public void setSources(List<Source__5> sources) {
        this.sources = sources;
    }

    public List<Anomaly__5> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__5> anomalies) {
        this.anomalies = anomalies;
    }

}
