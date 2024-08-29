
package org.jointheleague.features.whale.DataTrasnferObjects;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SearchParameters {

    @SerializedName("engine")
    @Expose
    private String engine;
    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("location_requested")
    @Expose
    private String locationRequested;
    @SerializedName("location_used")
    @Expose
    private String locationUsed;
    @SerializedName("google_domain")
    @Expose
    private String googleDomain;
    @SerializedName("hl")
    @Expose
    private String hl;
    @SerializedName("gl")
    @Expose
    private String gl;
    @SerializedName("device")
    @Expose
    private String device;

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getLocationRequested() {
        return locationRequested;
    }

    public void setLocationRequested(String locationRequested) {
        this.locationRequested = locationRequested;
    }

    public String getLocationUsed() {
        return locationUsed;
    }

    public void setLocationUsed(String locationUsed) {
        this.locationUsed = locationUsed;
    }

    public String getGoogleDomain() {
        return googleDomain;
    }

    public void setGoogleDomain(String googleDomain) {
        this.googleDomain = googleDomain;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
