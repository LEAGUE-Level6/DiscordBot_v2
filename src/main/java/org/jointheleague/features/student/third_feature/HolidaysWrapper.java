package org.jointheleague.features.student.third_feature;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "name",
            "name_local",
            "language",
            "description",
            "country",
            "location",
            "type",
            "date",
            "date_year",
            "date_month",
            "date_day",
            "week_day"
    })
    @Generated("jsonschema2pojo")
    public class HolidaysWrapper {
    @SerializedName("name")
    @Expose
        @JsonProperty("name")
        private String name;
        @JsonProperty("name_local")
        private String nameLocal;
        @JsonProperty("language")
        private String language;
        @JsonProperty("description")
        private String description;
        @JsonProperty("country")
        private String country;
        @JsonProperty("location")
        private String location;
        @JsonProperty("type")
        private String type;
        @JsonProperty("date")
        private String date;
        @JsonProperty("date_year")
        private String dateYear;
        @JsonProperty("date_month")
        private String dateMonth;
        @JsonProperty("date_day")
        private String dateDay;
        @JsonProperty("week_day")
        private String weekDay;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("name_local")
        public String getNameLocal() {
            return nameLocal;
        }

        @JsonProperty("name_local")
        public void setNameLocal(String nameLocal) {
            this.nameLocal = nameLocal;
        }

        @JsonProperty("language")
        public String getLanguage() {
            return language;
        }

        @JsonProperty("language")
        public void setLanguage(String language) {
            this.language = language;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        @JsonProperty("country")
        public void setCountry(String country) {
            this.country = country;
        }

        @JsonProperty("location")
        public String getLocation() {
            return location;
        }

        @JsonProperty("location")
        public void setLocation(String location) {
            this.location = location;
        }

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }

        @JsonProperty("date")
        public String getDate() {
            return date;
        }

        @JsonProperty("date")
        public void setDate(String date) {
            this.date = date;
        }

        @JsonProperty("date_year")
        public String getDateYear() {
            return dateYear;
        }

        @JsonProperty("date_year")
        public void setDateYear(String dateYear) {
            this.dateYear = dateYear;
        }

        @JsonProperty("date_month")
        public String getDateMonth() {
            return dateMonth;
        }

        @JsonProperty("date_month")
        public void setDateMonth(String dateMonth) {
            this.dateMonth = dateMonth;
        }

        @JsonProperty("date_day")
        public String getDateDay() {
            return dateDay;
        }

        @JsonProperty("date_day")
        public void setDateDay(String dateDay) {
            this.dateDay = dateDay;
        }

        @JsonProperty("week_day")
        public String getWeekDay() {
            return weekDay;
        }

        @JsonProperty("week_day")
        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

