
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
    "last",
    "best",
    "record"
})
@Generated("jsonschema2pojo")
public class Chess960Daily {

    @JsonProperty("last")
    private Last__1 last;
    @JsonProperty("best")
    private Best__1 best;
    @JsonProperty("record")
    private Record__1 record;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("last")
    public Last__1 getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Last__1 last) {
        this.last = last;
    }

    @JsonProperty("best")
    public Best__1 getBest() {
        return best;
    }

    @JsonProperty("best")
    public void setBest(Best__1 best) {
        this.best = best;
    }

    @JsonProperty("record")
    public Record__1 getRecord() {
        return record;
    }

    @JsonProperty("record")
    public void setRecord(Record__1 record) {
        this.record = record;
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
