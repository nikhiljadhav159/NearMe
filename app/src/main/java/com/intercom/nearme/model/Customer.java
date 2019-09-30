package com.intercom.nearme.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.InverseMethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Observable;

import butterknife.BindView;

public class Customer extends BaseObservable implements Comparable<Customer>{
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    private double distance=0;

    @Bindable
    public Double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Bindable
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Customer user) {
        int compareId = user.getUserId();
        return this.userId-compareId;
    }

//    @InverseMethod(value = "convertDistanceToString")
    public static String convertDistanceToString(double distance) {
        return Double.toString(distance)+" KMs";
    }
}
