
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinesAdministeredDemographics__1 {

    @SerializedName("age")
    @Expose
    private Age__2 age;
    @SerializedName("race")
    @Expose
    private Race__2 race;
    @SerializedName("ethnicity")
    @Expose
    private Ethnicity__2 ethnicity;
    @SerializedName("sex")
    @Expose
    private Sex__2 sex;

    public Age__2 getAge() {
        return age;
    }

    public void setAge(Age__2 age) {
        this.age = age;
    }

    public Race__2 getRace() {
        return race;
    }

    public void setRace(Race__2 race) {
        this.race = race;
    }

    public Ethnicity__2 getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity__2 ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Sex__2 getSex() {
        return sex;
    }

    public void setSex(Sex__2 sex) {
        this.sex = sex;
    }

}
