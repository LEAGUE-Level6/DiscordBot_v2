
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsInitiatedRatio {

    @SerializedName("sources")
    @Expose
    private List<Source__19> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__19> anomalies = null;

    public List<Source__19> getSources() {
        return sources;
    }

    public void setSources(List<Source__19> sources) {
        this.sources = sources;
    }

    public List<Anomaly__19> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__19> anomalies) {
        this.anomalies = anomalies;
    }

}
