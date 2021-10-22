
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CdcTransmissionLevelTimeseries {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("cdcTransmissionLevel")
    @Expose
    private Integer cdcTransmissionLevel;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCdcTransmissionLevel() {
        return cdcTransmissionLevel;
    }

    public void setCdcTransmissionLevel(Integer cdcTransmissionLevel) {
        this.cdcTransmissionLevel = cdcTransmissionLevel;
    }

}
