
package org.jointheleague.features.covid_tracker;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class VaccinesAdministeredDemographics {

    @SerializedName("age")
    @Expose
    private Age age;
    @SerializedName("race")
    @Expose
    private Race race;
    @SerializedName("ethnicity")
    @Expose
    private Ethnicity ethnicity;
    @SerializedName("sex")
    @Expose
    private Sex sex;

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

}
