
package org.jointheleague.features.whale.DataTrasnferObjects;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SerpapiPagination {

    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("next")
    @Expose
    private String next;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
