
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class RiskLevels {

    @SerializedName("overall")
    @Expose
    private Integer overall;
    @SerializedName("testPositivityRatio")
    @Expose
    private Integer testPositivityRatio;
    @SerializedName("caseDensity")
    @Expose
    private Integer caseDensity;
    @SerializedName("contactTracerCapacityRatio")
    @Expose
    private Integer contactTracerCapacityRatio;
    @SerializedName("infectionRate")
    @Expose
    private Integer infectionRate;
    @SerializedName("icuCapacityRatio")
    @Expose
    private Integer icuCapacityRatio;

    public Integer getOverall() {
        return overall;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public Integer getTestPositivityRatio() {
        return testPositivityRatio;
    }

    public void setTestPositivityRatio(Integer testPositivityRatio) {
        this.testPositivityRatio = testPositivityRatio;
    }

    public Integer getCaseDensity() {
        return caseDensity;
    }

    public void setCaseDensity(Integer caseDensity) {
        this.caseDensity = caseDensity;
    }

    public Integer getContactTracerCapacityRatio() {
        return contactTracerCapacityRatio;
    }

    public void setContactTracerCapacityRatio(Integer contactTracerCapacityRatio) {
        this.contactTracerCapacityRatio = contactTracerCapacityRatio;
    }

    public Integer getInfectionRate() {
        return infectionRate;
    }

    public void setInfectionRate(Integer infectionRate) {
        this.infectionRate = infectionRate;
    }

    public Integer getIcuCapacityRatio() {
        return icuCapacityRatio;
    }

    public void setIcuCapacityRatio(Integer icuCapacityRatio) {
        this.icuCapacityRatio = icuCapacityRatio;
    }

}
