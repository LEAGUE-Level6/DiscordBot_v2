
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinationsInitiatedDemographics {

    @SerializedName("age")
    @Expose
    private Age__1 age;
    @SerializedName("race")
    @Expose
    private Race__1 race;
    @SerializedName("ethnicity")
    @Expose
    private Ethnicity__1 ethnicity;
    @SerializedName("sex")
    @Expose
    private Sex__1 sex;

    public Age__1 getAge() {
        return age;
    }

    public void setAge(Age__1 age) {
        this.age = age;
    }

    public Race__1 getRace() {
        return race;
    }

    public void setRace(Race__1 race) {
        this.race = race;
    }

    public Ethnicity__1 getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity__1 ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Sex__1 getSex() {
        return sex;
    }

    public void setSex(Sex__1 sex) {
        this.sex = sex;
    }

}
