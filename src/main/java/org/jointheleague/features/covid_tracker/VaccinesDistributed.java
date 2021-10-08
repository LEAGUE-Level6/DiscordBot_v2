
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinesDistributed {

    @SerializedName("sources")
    @Expose
    private List<Source__9> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__9> anomalies = null;

    public List<Source__9> getSources() {
        return sources;
    }

    public void setSources(List<Source__9> sources) {
        this.sources = sources;
    }

    public List<Anomaly__9> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__9> anomalies) {
        this.anomalies = anomalies;
    }

}
