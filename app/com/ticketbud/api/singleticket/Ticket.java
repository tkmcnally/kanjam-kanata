
package com.ticketbud.api.singleticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "id",
    "created_at",
    "updated_at",
    "event_id",
    "first_name",
    "last_name",
    "name_on_ticket",
    "email",
    "checked_in",
    "barcode",
    "barcode_url",
    "ticket_type",
    "price_paid",
    "user",
    "purchaser",
    "custom_fields"
})
public class Ticket {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("name_on_ticket")
    private String nameOnTicket;
    @JsonProperty("email")
    private String email;
    @JsonProperty("checked_in")
    private Boolean checkedIn;
    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("barcode_url")
    private String barcodeUrl;
    @JsonProperty("ticket_type")
    private String ticketType;
    @JsonProperty("price_paid")
    private String pricePaid;
    @JsonProperty("user")
    private User user;
    @JsonProperty("purchaser")
    private Purchaser purchaser;
    @JsonProperty("custom_fields")
    private List<Object> customFields = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The eventId
     */
    @JsonProperty("event_id")
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 
     * @param eventId
     *     The event_id
     */
    @JsonProperty("event_id")
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The nameOnTicket
     */
    @JsonProperty("name_on_ticket")
    public String getNameOnTicket() {
        return nameOnTicket;
    }

    /**
     * 
     * @param nameOnTicket
     *     The name_on_ticket
     */
    @JsonProperty("name_on_ticket")
    public void setNameOnTicket(String nameOnTicket) {
        this.nameOnTicket = nameOnTicket;
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
     *     The checkedIn
     */
    @JsonProperty("checked_in")
    public Boolean getCheckedIn() {
        return checkedIn;
    }

    /**
     * 
     * @param checkedIn
     *     The checked_in
     */
    @JsonProperty("checked_in")
    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     * 
     * @return
     *     The barcode
     */
    @JsonProperty("barcode")
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * @param barcode
     *     The barcode
     */
    @JsonProperty("barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 
     * @return
     *     The barcodeUrl
     */
    @JsonProperty("barcode_url")
    public String getBarcodeUrl() {
        return barcodeUrl;
    }

    /**
     * 
     * @param barcodeUrl
     *     The barcode_url
     */
    @JsonProperty("barcode_url")
    public void setBarcodeUrl(String barcodeUrl) {
        this.barcodeUrl = barcodeUrl;
    }

    /**
     * 
     * @return
     *     The ticketType
     */
    @JsonProperty("ticket_type")
    public String getTicketType() {
        return ticketType;
    }

    /**
     * 
     * @param ticketType
     *     The ticket_type
     */
    @JsonProperty("ticket_type")
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
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
     *     The user
     */
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The purchaser
     */
    @JsonProperty("purchaser")
    public Purchaser getPurchaser() {
        return purchaser;
    }

    /**
     * 
     * @param purchaser
     *     The purchaser
     */
    @JsonProperty("purchaser")
    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    /**
     * 
     * @return
     *     The customFields
     */
    @JsonProperty("custom_fields")
    public List<Object> getCustomFields() {
        return customFields;
    }

    /**
     * 
     * @param customFields
     *     The custom_fields
     */
    @JsonProperty("custom_fields")
    public void setCustomFields(List<Object> customFields) {
        this.customFields = customFields;
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
