package com.intercom.nearme.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private MainViewModel mainViewModel;
    @Mock
    private Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(application);
    }

    @Test
    public void checkValidInput() {
        String inputString = "150";
        MutableLiveData<String> validInputString = new MutableLiveData<>();
        MutableLiveData<Boolean> isValidInput = new MutableLiveData<>();

        validInputString = mainViewModel.getValidInputString();
        isValidInput = mainViewModel.getIsValidInput();
        mainViewModel.checkValidInput(inputString);

        if (isValidInput.getValue()) {
            assertTrue("Valid Input", true);
            Log.d("checkValidInput","Valid Input");
        } else {
            assertFalse("Invalid Input", false);
            Log.d("checkValidInput","Invalid Input");
        }

        if ((inputString.equalsIgnoreCase(validInputString.getValue()))) {
            assertTrue("Valid Input String " + validInputString.getValue(), true);
            Log.d("checkValidInput","Valid Input String " + validInputString.getValue());
        } else {
            assertFalse("Invalid Input String " + validInputString.getValue(), false);
            Log.d("checkValidInput","Invalid Input String "+ validInputString.getValue());
        }

    }

    @Test
    public void checkForCustomerList() {
        MutableLiveData<Boolean> isValidCustomerList = new MutableLiveData<>();

        isValidCustomerList = mainViewModel.getIsValidCustomerList();

        mainViewModel.checkForCustomerList(-1);
        if (isValidCustomerList.getValue()) {
            assertTrue("Valid Customer List", true);
            Log.d("checkForCustomerList","Valid Customer List");
        } else {
            assertFalse("Invalid Customer List", false);
            Log.d("checkForCustomerList","Invalid Customer List");
        }

    }

    @Test
    public void distance() {
        double puneLat = 18.516726;
        double puneLon = 73.856255;

        double dist = mainViewModel.distance(puneLat,puneLon);

        if(dist==7715.72){
            assertTrue("Right Distance Calculated "+dist,true);
            Log.d("Distance","Right Distance Calculated "+dist);
        }else{
            assertFalse("Wrong Distance Calculated "+dist,false);
            Log.d("Distance","Wrong Distance Calculated "+dist);
        }

    }
}