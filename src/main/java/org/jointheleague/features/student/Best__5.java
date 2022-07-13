
package org.jointheleague.features.student;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total_attempts",
    "score"
})
@Generated("jsonschema2pojo")
public class Best__5 {

    @JsonProperty("total_attempts")
    private Integer totalAttempts;
    @JsonProperty("score")
    private Integer score;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("total_attempts")
    public Integer getTotalAttempts() {
        return totalAttempts;
    }

    @JsonProperty("total_attempts")
    public void setTotalAttempts(Integer totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    @JsonProperty("score")
    public Integer getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Integer score) {
        this.score = score;
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
