package com.intercom.nearme.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.intercom.nearme.model.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class MainViewModel extends AndroidViewModel {

    private Context appContext;
    private MutableLiveData<ArrayList<Customer>> originalCustomerList = new MutableLiveData<>();
    private MutableLiveData<String> validInputString = new MutableLiveData<>();
    private MutableLiveData<Boolean> isValidInput = new MutableLiveData<>();
    private MutableLiveData<Boolean> isValidCustomerList = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsValidCustomerList() {
        return isValidCustomerList;
    }

    public MutableLiveData<String> getValidInputString() {
        return validInputString;
    }

    public MutableLiveData<Boolean> getIsValidInput() {
        return isValidInput;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        appContext = application;
    }

    public MutableLiveData<ArrayList<Customer>> getOriginalCustomerList() {
        return originalCustomerList;
    }

    public void readCustomerDetails() {
        BufferedReader reader = null;
        Customer customer;
        Gson gson = new Gson();
        ArrayList<Customer> customerList = new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(appContext.getAssets().open("customers.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                customer = gson.fromJson(mLine, Customer.class);
                customer.setDistance(distance(customer.getLatitude(), customer.getLongitude()));
                customerList.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            originalCustomerList.setValue(new ArrayList<>());
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    if (customerList.size() > 0) {
                        Collections.sort(customerList);
                        originalCustomerList.setValue(customerList);
                    } else {
                        originalCustomerList.setValue(new ArrayList<>());
                    }

                } catch (IOException e) {
                    //log the exception
                    e.printStackTrace();
                    originalCustomerList.setValue(new ArrayList<>());
                }
            }
        }
    }

    public double distance(double lat2, double lon2) {
        double lon1 = -6.257664;
        double theta = lon1 - lon2;
        double lat1 = 53.339428;
        double d2rLat1 = deg2rad(lat1);
        double d2rLat2 = deg2rad(lat2);
        double dist = (Math.sin(d2rLat1)
                * Math.sin(d2rLat2))
                + (Math.cos(d2rLat1)
                * Math.cos(d2rLat2)
                * Math.cos(deg2rad(theta)));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        BigDecimal bd = new BigDecimal(dist).setScale(2, RoundingMode.HALF_UP);
        return (bd.doubleValue());
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void checkValidInput(String text) {
        if (text != null && !text.isEmpty()) {
            isValidInput.setValue(true);
            validInputString.setValue(text.toString());
        } else {
            isValidInput.setValue(false);
        }
    }

    public void checkForCustomerList(int size) {
        if (size > 0) {
            isValidCustomerList.setValue(true);
        } else {
            isValidCustomerList.setValue(false);
        }

    }
}
