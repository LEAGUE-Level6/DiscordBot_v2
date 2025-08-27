package org.jointheleague.features.student.first_feature;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Current {

@SerializedName("last_updated_epoch")
@Expose
private Integer lastUpdatedEpoch;
@SerializedName("last_updated")
@Expose
private String lastUpdated;
@SerializedName("temp_c")
@Expose
private Float tempC;
@SerializedName("temp_f")
@Expose
private Float tempF;
@SerializedName("is_day")
@Expose
private Integer isDay;
@SerializedName("condition")
@Expose
private Condition condition;
@SerializedName("wind_mph")
@Expose
private Float windMph;
@SerializedName("wind_kph")
@Expose
private Float windKph;
@SerializedName("wind_degree")
@Expose
private Integer windDegree;
@SerializedName("wind_dir")
@Expose
private String windDir;
@SerializedName("pressure_mb")
@Expose
private Integer pressureMb;
@SerializedName("pressure_in")
@Expose
private Float pressureIn;
@SerializedName("precip_mm")
@Expose
private Integer precipMm;
@SerializedName("precip_in")
@Expose
private Integer precipIn;
@SerializedName("humidity")
@Expose
private Integer humidity;
@SerializedName("cloud")
@Expose
private Integer cloud;
@SerializedName("feelslike_c")
@Expose
private Integer feelslikeC;
@SerializedName("feelslike_f")
@Expose
private Float feelslikeF;
@SerializedName("vis_km")
@Expose
private Integer visKm;
@SerializedName("vis_miles")
@Expose
private Integer visMiles;
@SerializedName("uv")
@Expose
private Float uv;
@SerializedName("gust_mph")
@Expose
private Float gustMph;
@SerializedName("gust_kph")
@Expose
private Float gustKph;
//@SerializedName("air_quality")
//@Expose
//private AirQuality airQuality;

public Integer getLastUpdatedEpoch() {
return lastUpdatedEpoch;
}

public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
this.lastUpdatedEpoch = lastUpdatedEpoch;
}

public String getLastUpdated() {
return lastUpdated;
}

public void setLastUpdated(String lastUpdated) {
this.lastUpdated = lastUpdated;
}

public Float getTempC() {
return tempC;
}

public void setTempC(Float tempC) {
this.tempC = tempC;
}

public Float getTempF() {
return tempF;
}

public void setTempF(Float tempF) {
this.tempF = tempF;
}

public Integer getIsDay() {
return isDay;
}

public void setIsDay(Integer isDay) {
this.isDay = isDay;
}

public Condition getCondition() {
return condition;
}

public void setCondition(Condition condition) {
this.condition = condition;
}

public Float getWindMph() {
return windMph;
}

public void setWindMph(Float windMph) {
this.windMph = windMph;
}

public Float getWindKph() {
return windKph;
}

public void setWindKph(Float windKph) {
this.windKph = windKph;
}

public Integer getWindDegree() {
return windDegree;
}

public void setWindDegree(Integer windDegree) {
this.windDegree = windDegree;
}

public String getWindDir() {
return windDir;
}

public void setWindDir(String windDir) {
this.windDir = windDir;
}

public Integer getPressureMb() {
return pressureMb;
}

public void setPressureMb(Integer pressureMb) {
this.pressureMb = pressureMb;
}

public Float getPressureIn() {
return pressureIn;
}

public void setPressureIn(Float pressureIn) {
this.pressureIn = pressureIn;
}

public Integer getPrecipMm() {
return precipMm;
}

public void setPrecipMm(Integer precipMm) {
this.precipMm = precipMm;
}

public Integer getPrecipIn() {
return precipIn;
}

public void setPrecipIn(Integer precipIn) {
this.precipIn = precipIn;
}

public Integer getHumidity() {
return humidity;
}

public void setHumidity(Integer humidity) {
this.humidity = humidity;
}

public Integer getCloud() {
return cloud;
}

public void setCloud(Integer cloud) {
this.cloud = cloud;
}

public Integer getFeelslikeC() {
return feelslikeC;
}

public void setFeelslikeC(Integer feelslikeC) {
this.feelslikeC = feelslikeC;
}

public Float getFeelslikeF() {
return feelslikeF;
}

public void setFeelslikeF(Float feelslikeF) {
this.feelslikeF = feelslikeF;
}

public Integer getVisKm() {
return visKm;
}

public void setVisKm(Integer visKm) {
this.visKm = visKm;
}

public Integer getVisMiles() {
return visMiles;
}

public void setVisMiles(Integer visMiles) {
this.visMiles = visMiles;
}

public Float getUv() {
return uv;
}

public void setUv(Float uv) {
this.uv = uv;
}

public Float getGustMph() {
return gustMph;
}

public void setGustMph(Float gustMph) {
this.gustMph = gustMph;
}

public Float getGustKph() {
return gustKph;
}

public void setGustKph(Float gustKph) {
this.gustKph = gustKph;
}

@Override
public String toString() {
	return "Current [lastUpdatedEpoch=" + lastUpdatedEpoch + ", lastUpdated=" + lastUpdated + ", tempC=" + tempC
			+ ", tempF=" + tempF + ", isDay=" + isDay + ", condition=" + condition + ", windMph=" + windMph
			+ ", windKph=" + windKph + ", windDegree=" + windDegree + ", windDir=" + windDir + ", pressureMb="
			+ pressureMb + ", pressureIn=" + pressureIn + ", precipMm=" + precipMm + ", precipIn=" + precipIn
			+ ", humidity=" + humidity + ", cloud=" + cloud + ", feelslikeC=" + feelslikeC + ", feelslikeF="
			+ feelslikeF + ", visKm=" + visKm + ", visMiles=" + visMiles + ", uv=" + uv + ", gustMph=" + gustMph
			+ ", gustKph=" + gustKph + "]";
}

//public AirQuality getAirQuality() {
//return airQuality;
//}
//
//public void setAirQuality(AirQuality airQuality) {
//this.airQuality = airQuality;
//}

}