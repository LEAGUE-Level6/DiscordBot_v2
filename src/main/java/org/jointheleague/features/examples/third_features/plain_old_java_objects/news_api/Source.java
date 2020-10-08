package org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {
	
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("name")
	@Expose
	private String name;

	public String getId() {
	return id;
	}

	public void setId(String id) {
	this.id = id;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

}
