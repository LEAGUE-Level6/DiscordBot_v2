
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsCompleted {

    @SerializedName("sources")
    @Expose
    private List<Source__11> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__11> anomalies = null;

    public List<Source__11> getSources() {
        return sources;
    }

    public void setSources(List<Source__11> sources) {
        this.sources = sources;
    }

    public List<Anomaly__11> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__11> anomalies) {
        this.anomalies = anomalies;
    }

}
