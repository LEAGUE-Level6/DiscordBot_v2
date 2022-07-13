
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
    "win",
    "loss",
    "draw",
    "time_per_move",
    "timeout_percent"
})
@Generated("jsonschema2pojo")
public class Record {

    @JsonProperty("win")
    private Integer win;
    @JsonProperty("loss")
    private Integer loss;
    @JsonProperty("draw")
    private Integer draw;
    @JsonProperty("time_per_move")
    private Integer timePerMove;
    @JsonProperty("timeout_percent")
    private Integer timeoutPercent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("win")
    public Integer getWin() {
        return win;
    }

    @JsonProperty("win")
    public void setWin(Integer win) {
        this.win = win;
    }

    @JsonProperty("loss")
    public Integer getLoss() {
        return loss;
    }

    @JsonProperty("loss")
    public void setLoss(Integer loss) {
        this.loss = loss;
    }

    @JsonProperty("draw")
    public Integer getDraw() {
        return draw;
    }

    @JsonProperty("draw")
    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    @JsonProperty("time_per_move")
    public Integer getTimePerMove() {
        return timePerMove;
    }

    @JsonProperty("time_per_move")
    public void setTimePerMove(Integer timePerMove) {
        this.timePerMove = timePerMove;
    }

    @JsonProperty("timeout_percent")
    public Integer getTimeoutPercent() {
        return timeoutPercent;
    }

    @JsonProperty("timeout_percent")
    public void setTimeoutPercent(Integer timeoutPercent) {
        this.timeoutPercent = timeoutPercent;
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
