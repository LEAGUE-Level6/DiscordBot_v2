
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
    "last",
    "best",
    "record"
})
@Generated("jsonschema2pojo")
public class ChessRapid {

    @JsonProperty("last")
    private Last__2 last;
    @JsonProperty("best")
    private Best__2 best;
    @JsonProperty("record")
    private Record__2 record;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("last")
    public Last__2 getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Last__2 last) {
        this.last = last;
    }

    @JsonProperty("best")
    public Best__2 getBest() {
        return best;
    }

    @JsonProperty("best")
    public void setBest(Best__2 best) {
        this.best = best;
    }

    @JsonProperty("record")
    public Record__2 getRecord() {
        return record;
    }

    @JsonProperty("record")
    public void setRecord(Record__2 record) {
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
