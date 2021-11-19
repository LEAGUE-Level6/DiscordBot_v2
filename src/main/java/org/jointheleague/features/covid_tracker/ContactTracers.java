
package org.jointheleague.features.covid_tracker;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ContactTracers {

    @SerializedName("sources")
    @Expose
    private List<Source__4> sources = null;
    @SerializedName("anomalies")
    @Expose
    private List<Anomaly__4> anomalies = null;

    public List<Source__4> getSources() {
        return sources;
    }

    public void setSources(List<Source__4> sources) {
        this.sources = sources;
    }

    public List<Anomaly__4> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly__4> anomalies) {
        this.anomalies = anomalies;
    }

}
