
package com.tatitati.meterreadingapp.database.database_objects;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Meter {

    @SerializedName("createDate")
    private Long mCreateDate;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("meterNumber")
    private String mMeterNumber;
    @SerializedName("meterReadings")
    private List<MeterReading> mMeterReadings;
    @SerializedName("meterType")
    private MeterType mMeterType;
    @SerializedName("serviceName")
    private Service mService;

    public Meter(Long mCreateDate, String mMeterNumber, MeterType mMeterType) {
        this.mCreateDate = mCreateDate;
        this.mMeterNumber = mMeterNumber;
        this.mMeterType = mMeterType;
    }

    public Long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Long createDate) {
        mCreateDate = createDate;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getMeterNumber() {
        return mMeterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        mMeterNumber = meterNumber;
    }

    public List<MeterReading> getMeterReadings() {
        return mMeterReadings;
    }

    public void setMeterReadings(List<MeterReading> meterReadings) {
        mMeterReadings = meterReadings;
    }

    public MeterType getMeterType() {
        return mMeterType;
    }

    public void setMeterType(MeterType meterType) {
        mMeterType = meterType;
    }

    public Service getmService() {
        return mService;
    }

    public void setmService(Service mService) {
        this.mService = mService;
    }
}
