
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Deaths {

    @SerializedName("sources")
    @Expose
    private List<Source__1> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__1> anomalies = null;

    public List<Source__1> getSources() {
        return sources;
    }

    public void setSources(List<Source__1> sources) {
        this.sources = sources;
    }

    public List<Anomaly__1> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__1> anomalies) {
        this.anomalies = anomalies;
    }

}
