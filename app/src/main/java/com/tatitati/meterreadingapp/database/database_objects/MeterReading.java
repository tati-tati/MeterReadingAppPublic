
package com.tatitati.meterreadingapp.database.database_objects;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MeterReading implements Comparable <MeterReading> {

    @SerializedName("date")
    private Long mDate;
    @SerializedName("id")
    private Integer mId;
    @SerializedName("value")
    private Double mValue;

    public Long getDate() {
        return mDate;
    }

    public void setDate(Long date) {
        mDate = date;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Double getValue() {
        return mValue;
    }

    public void setValue(Double value) {
        mValue = value;
    }

    @Override
    public int compareTo(MeterReading o) {
        return Long.compare(this.getDate(), o.getDate());
    }
}
