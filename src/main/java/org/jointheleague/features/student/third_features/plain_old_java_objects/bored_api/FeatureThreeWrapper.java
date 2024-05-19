package org.jointheleague.features.student.third_features.plain_old_java_objects.bored_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class FeatureThreeWrapper {
	@SerializedName("data")
	@Expose
	private List<String> data = null;
	
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}

	@SerializedName("activity")
	@Expose
	private String activity;

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

}
