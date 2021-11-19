
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsInitiatedDemographics__1 {

    @SerializedName("age")
    @Expose
    private Age__3 age;
    @SerializedName("race")
    @Expose
    private Race__3 race;
    @SerializedName("ethnicity")
    @Expose
    private Ethnicity__3 ethnicity;
    @SerializedName("sex")
    @Expose
    private Sex__3 sex;

    public Age__3 getAge() {
        return age;
    }

    public void setAge(Age__3 age) {
        this.age = age;
    }

    public Race__3 getRace() {
        return race;
    }

    public void setRace(Race__3 race) {
        this.race = race;
    }

    public Ethnicity__3 getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity__3 ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Sex__3 getSex() {
        return sex;
    }

    public void setSex(Sex__3 sex) {
        this.sex = sex;
    }

}
