
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MetricsTimeseries {

    @SerializedName("testPositivityRatio")
    @Expose
    private Integer testPositivityRatio;
    @SerializedName("testPositivityRatioDetails")
    @Expose
    private TestPositivityRatioDetails__1 testPositivityRatioDetails;
    @SerializedName("caseDensity")
    @Expose
    private Integer caseDensity;
    @SerializedName("contactTracerCapacityRatio")
    @Expose
    private Integer contactTracerCapacityRatio;
    @SerializedName("infectionRate")
    @Expose
    private Integer infectionRate;
    @SerializedName("infectionRateCI90")
    @Expose
    private Integer infectionRateCI90;
    @SerializedName("icuCapacityRatio")
    @Expose
    private Integer icuCapacityRatio;
    @SerializedName("vaccinationsInitiatedRatio")
    @Expose
    private Integer vaccinationsInitiatedRatio;
    @SerializedName("vaccinationsCompletedRatio")
    @Expose
    private Integer vaccinationsCompletedRatio;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getTestPositivityRatio() {
        return testPositivityRatio;
    }

    public void setTestPositivityRatio(Integer testPositivityRatio) {
        this.testPositivityRatio = testPositivityRatio;
    }

    public TestPositivityRatioDetails__1 getTestPositivityRatioDetails() {
        return testPositivityRatioDetails;
    }

    public void setTestPositivityRatioDetails(TestPositivityRatioDetails__1 testPositivityRatioDetails) {
        this.testPositivityRatioDetails = testPositivityRatioDetails;
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

    public Integer getInfectionRateCI90() {
        return infectionRateCI90;
    }

    public void setInfectionRateCI90(Integer infectionRateCI90) {
        this.infectionRateCI90 = infectionRateCI90;
    }

    public Integer getIcuCapacityRatio() {
        return icuCapacityRatio;
    }

    public void setIcuCapacityRatio(Integer icuCapacityRatio) {
        this.icuCapacityRatio = icuCapacityRatio;
    }

    public Integer getVaccinationsInitiatedRatio() {
        return vaccinationsInitiatedRatio;
    }

    public void setVaccinationsInitiatedRatio(Integer vaccinationsInitiatedRatio) {
        this.vaccinationsInitiatedRatio = vaccinationsInitiatedRatio;
    }

    public Integer getVaccinationsCompletedRatio() {
        return vaccinationsCompletedRatio;
    }

    public void setVaccinationsCompletedRatio(Integer vaccinationsCompletedRatio) {
        this.vaccinationsCompletedRatio = vaccinationsCompletedRatio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
