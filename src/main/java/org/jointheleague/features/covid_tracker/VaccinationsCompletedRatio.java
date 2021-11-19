
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsCompletedRatio {

    @SerializedName("sources")
    @Expose
    private List<Source__20> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__20> anomalies = null;

    public List<Source__20> getSources() {
        return sources;
    }

    public void setSources(List<Source__20> sources) {
        this.sources = sources;
    }

    public List<Anomaly__20> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__20> anomalies) {
        this.anomalies = anomalies;
    }

}
