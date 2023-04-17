
package org.jointheleague.features.student.wrappers;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jointheleague.features.student.wrappers.Current;
import org.jointheleague.features.student.wrappers.Location;

@Generated("jsonschema2pojo")
public class WeatherWrapper {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

}
