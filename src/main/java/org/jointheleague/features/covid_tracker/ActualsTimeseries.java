
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ActualsTimeseries {

    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("positiveTests")
    @Expose
    private Integer positiveTests;
    @SerializedName("negativeTests")
    @Expose
    private Integer negativeTests;
    @SerializedName("contactTracers")
    @Expose
    private Integer contactTracers;
    @SerializedName("hospitalBeds")
    @Expose
    private HospitalBeds__2 hospitalBeds;
    @SerializedName("icuBeds")
    @Expose
    private IcuBeds__2 icuBeds;
    @SerializedName("newCases")
    @Expose
    private Integer newCases;
    @SerializedName("newDeaths")
    @Expose
    private Integer newDeaths;
    @SerializedName("vaccinesDistributed")
    @Expose
    private Integer vaccinesDistributed;
    @SerializedName("vaccinationsInitiated")
    @Expose
    private Integer vaccinationsInitiated;
    @SerializedName("vaccinationsCompleted")
    @Expose
    private Integer vaccinationsCompleted;
    @SerializedName("vaccinesAdministered")
    @Expose
    private Integer vaccinesAdministered;
    @SerializedName("vaccinesAdministeredDemographics")
    @Expose
    private VaccinesAdministeredDemographics__1 vaccinesAdministeredDemographics;
    @SerializedName("vaccinationsInitiatedDemographics")
    @Expose
    private VaccinationsInitiatedDemographics__1 vaccinationsInitiatedDemographics;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getPositiveTests() {
        return positiveTests;
    }

    public void setPositiveTests(Integer positiveTests) {
        this.positiveTests = positiveTests;
    }

    public Integer getNegativeTests() {
        return negativeTests;
    }

    public void setNegativeTests(Integer negativeTests) {
        this.negativeTests = negativeTests;
    }

    public Integer getContactTracers() {
        return contactTracers;
    }

    public void setContactTracers(Integer contactTracers) {
        this.contactTracers = contactTracers;
    }

    public HospitalBeds__2 getHospitalBeds() {
        return hospitalBeds;
    }

    public void setHospitalBeds(HospitalBeds__2 hospitalBeds) {
        this.hospitalBeds = hospitalBeds;
    }

    public IcuBeds__2 getIcuBeds() {
        return icuBeds;
    }

    public void setIcuBeds(IcuBeds__2 icuBeds) {
        this.icuBeds = icuBeds;
    }

    public Integer getNewCases() {
        return newCases;
    }

    public void setNewCases(Integer newCases) {
        this.newCases = newCases;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Integer getVaccinesDistributed() {
        return vaccinesDistributed;
    }

    public void setVaccinesDistributed(Integer vaccinesDistributed) {
        this.vaccinesDistributed = vaccinesDistributed;
    }

    public Integer getVaccinationsInitiated() {
        return vaccinationsInitiated;
    }

    public void setVaccinationsInitiated(Integer vaccinationsInitiated) {
        this.vaccinationsInitiated = vaccinationsInitiated;
    }

    public Integer getVaccinationsCompleted() {
        return vaccinationsCompleted;
    }

    public void setVaccinationsCompleted(Integer vaccinationsCompleted) {
        this.vaccinationsCompleted = vaccinationsCompleted;
    }

    public Integer getVaccinesAdministered() {
        return vaccinesAdministered;
    }

    public void setVaccinesAdministered(Integer vaccinesAdministered) {
        this.vaccinesAdministered = vaccinesAdministered;
    }

    public VaccinesAdministeredDemographics__1 getVaccinesAdministeredDemographics() {
        return vaccinesAdministeredDemographics;
    }

    public void setVaccinesAdministeredDemographics(VaccinesAdministeredDemographics__1 vaccinesAdministeredDemographics) {
        this.vaccinesAdministeredDemographics = vaccinesAdministeredDemographics;
    }

    public VaccinationsInitiatedDemographics__1 getVaccinationsInitiatedDemographics() {
        return vaccinationsInitiatedDemographics;
    }

    public void setVaccinationsInitiatedDemographics(VaccinationsInitiatedDemographics__1 vaccinationsInitiatedDemographics) {
        this.vaccinationsInitiatedDemographics = vaccinationsInitiatedDemographics;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
