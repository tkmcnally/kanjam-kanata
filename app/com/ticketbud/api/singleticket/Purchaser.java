
package com.ticketbud.api.singleticket;

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
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "status",
    "full_name",
    "email",
    "price_paid",
    "total_quantity"
})
public class Purchaser {

    @JsonProperty("status")
    private String status;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("price_paid")
    private String pricePaid;
    @JsonProperty("total_quantity")
    private Integer totalQuantity;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The fullName
     */
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The full_name
     */
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The pricePaid
     */
    @JsonProperty("price_paid")
    public String getPricePaid() {
        return pricePaid;
    }

    /**
     * 
     * @param pricePaid
     *     The price_paid
     */
    @JsonProperty("price_paid")
    public void setPricePaid(String pricePaid) {
        this.pricePaid = pricePaid;
    }

    /**
     * 
     * @return
     *     The totalQuantity
     */
    @JsonProperty("total_quantity")
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * 
     * @param totalQuantity
     *     The total_quantity
     */
    @JsonProperty("total_quantity")
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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
