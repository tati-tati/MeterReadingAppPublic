
package com.tatitati.meterreadingapp.database.database_objects;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class User {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("meters")
    private List<Meter> mMeters;
    @SerializedName("middleName")
    private String mMiddleName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("registrationDate")
    private Long mRegistrationDate;
    @SerializedName("userServices")
    private List<Service> mServices;
    @SerializedName("username")
    private String mUsername;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public List<Meter> getMeters() {
        return mMeters;
    }

    public void setMeters(List<Meter> meters) {
        mMeters = meters;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Long getRegistrationDate() {
        return mRegistrationDate;
    }

    public void setRegistrationDate(Long registrationDate) {
        mRegistrationDate = registrationDate;
    }

    public List<Service> getUserServices() {
        return mServices;
    }

    public void setUserServices(List<Service> services) {
        mServices = services;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
