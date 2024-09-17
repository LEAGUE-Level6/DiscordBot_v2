package org.jointheleague.features.examples.third_features.plain_old_java_objects;

import com.google.gson.annotations.SerializedName;

public class AnimalWrapper {
    @SerializedName("animal")
    public String animal;
    @SerializedName("fact")
    public String fact;
    @SerializedName("source")
    public String source;
}
