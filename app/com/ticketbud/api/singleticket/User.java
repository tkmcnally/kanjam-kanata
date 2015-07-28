
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
    "full_name",
    "first_name",
    "last_name",
    "email",
    "default_subdomain",
    "image",
    "pos_account",
    "organizations",
    "event_ids"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("first_name")
    private Object firstName;
    @JsonProperty("last_name")
    private Object lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("default_subdomain")
    private Object defaultSubdomain;
    @JsonProperty("image")
    private String image;
    @JsonProperty("pos_account")
    private Object posAccount;
    @JsonProperty("organizations")
    private Organizations organizations;
    @JsonProperty("event_ids")
    private List<Integer> eventIds = new ArrayList<Integer>();
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
     *     The firstName
     */
    @JsonProperty("first_name")
    public Object getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("last_name")
    public Object getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(Object lastName) {
        this.lastName = lastName;
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
     *     The defaultSubdomain
     */
    @JsonProperty("default_subdomain")
    public Object getDefaultSubdomain() {
        return defaultSubdomain;
    }

    /**
     * 
     * @param defaultSubdomain
     *     The default_subdomain
     */
    @JsonProperty("default_subdomain")
    public void setDefaultSubdomain(Object defaultSubdomain) {
        this.defaultSubdomain = defaultSubdomain;
    }

    /**
     * 
     * @return
     *     The image
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The posAccount
     */
    @JsonProperty("pos_account")
    public Object getPosAccount() {
        return posAccount;
    }

    /**
     * 
     * @param posAccount
     *     The pos_account
     */
    @JsonProperty("pos_account")
    public void setPosAccount(Object posAccount) {
        this.posAccount = posAccount;
    }

    /**
     * 
     * @return
     *     The organizations
     */
    @JsonProperty("organizations")
    public Organizations getOrganizations() {
        return organizations;
    }

    /**
     * 
     * @param organizations
     *     The organizations
     */
    @JsonProperty("organizations")
    public void setOrganizations(Organizations organizations) {
        this.organizations = organizations;
    }

    /**
     * 
     * @return
     *     The eventIds
     */
    @JsonProperty("event_ids")
    public List<Integer> getEventIds() {
        return eventIds;
    }

    /**
     * 
     * @param eventIds
     *     The event_ids
     */
    @JsonProperty("event_ids")
    public void setEventIds(List<Integer> eventIds) {
        this.eventIds = eventIds;
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
