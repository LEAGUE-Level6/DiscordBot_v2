
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RiskLevelsTimeseries {

    @SerializedName("overall")
    @Expose
    private Integer overall;
    @SerializedName("caseDensity")
    @Expose
    private Integer caseDensity;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getOverall() {
        return overall;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public Integer getCaseDensity() {
        return caseDensity;
    }

    public void setCaseDensity(Integer caseDensity) {
        this.caseDensity = caseDensity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
