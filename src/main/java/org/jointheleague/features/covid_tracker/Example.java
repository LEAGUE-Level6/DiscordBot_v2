
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Example {

    @SerializedName("fips")
    @Expose
    private String fips;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("locationId")
    @Expose
    private String locationId;
    @SerializedName("long")
    @Expose
    private Integer _long;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("metrics")
    @Expose
    private Metrics metrics;
    @SerializedName("riskLevels")
    @Expose
    private RiskLevels riskLevels;
    @SerializedName("cdcTransmissionLevel")
    @Expose
    private Integer cdcTransmissionLevel;
    @SerializedName("actuals")
    @Expose
    private Actuals actuals;
    @SerializedName("annotations")
    @Expose
    private Annotations annotations;
    @SerializedName("lastUpdatedDate")
    @Expose
    private String lastUpdatedDate;
    @SerializedName("url")
    @Expose
    private String url;

    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getLong() {
        return _long;
    }

    public void setLong(Integer _long) {
        this._long = _long;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public RiskLevels getRiskLevels() {
        return riskLevels;
    }

    public void setRiskLevels(RiskLevels riskLevels) {
        this.riskLevels = riskLevels;
    }

    public Integer getCdcTransmissionLevel() {
        return cdcTransmissionLevel;
    }

    public void setCdcTransmissionLevel(Integer cdcTransmissionLevel) {
        this.cdcTransmissionLevel = cdcTransmissionLevel;
    }

    public Actuals getActuals() {
        return actuals;
    }

    public void setActuals(Actuals actuals) {
        this.actuals = actuals;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
