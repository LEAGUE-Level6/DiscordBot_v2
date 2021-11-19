
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinesAdministered {

    @SerializedName("sources")
    @Expose
    private List<Source__12> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__12> anomalies = null;

    public List<Source__12> getSources() {
        return sources;
    }

    public void setSources(List<Source__12> sources) {
        this.sources = sources;
    }

    public List<Anomaly__12> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__12> anomalies) {
        this.anomalies = anomalies;
    }

}
