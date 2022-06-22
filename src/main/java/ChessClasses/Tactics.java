
package ChessClasses;

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
    "highest",
    "lowest"
})
@Generated("jsonschema2pojo")
public class Tactics {

    @JsonProperty("highest")
    private Highest highest;
    @JsonProperty("lowest")
    private Lowest lowest;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("highest")
    public Highest getHighest() {
        return highest;
    }

    @JsonProperty("highest")
    public void setHighest(Highest highest) {
        this.highest = highest;
    }

    @JsonProperty("lowest")
    public Lowest getLowest() {
        return lowest;
    }

    @JsonProperty("lowest")
    public void setLowest(Lowest lowest) {
        this.lowest = lowest;
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
