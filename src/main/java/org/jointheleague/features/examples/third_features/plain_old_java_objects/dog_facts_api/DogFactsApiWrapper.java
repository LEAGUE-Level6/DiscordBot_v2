package org.jointheleague.features.examples.third_features.plain_old_java_objects.dog_facts_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogFactsApiWrapper {

	@SerializedName("fact")
	@Expose
	private String fact;

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

}