
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NewCases {

    @SerializedName("sources")
    @Expose
    private List<Source__7> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__7> anomalies = null;

    public List<Source__7> getSources() {
        return sources;
    }

    public void setSources(List<Source__7> sources) {
        this.sources = sources;
    }

    public List<Anomaly__7> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__7> anomalies) {
        this.anomalies = anomalies;
    }

}
