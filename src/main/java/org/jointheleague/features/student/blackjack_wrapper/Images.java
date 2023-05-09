
package org.jointheleague.features.student.blackjack_wrapper;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
public class Images {

    @JsonProperty("svg")
    @Expose
    private String svg;
    @JsonProperty("png")
    @Expose
    private String png;

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

}
