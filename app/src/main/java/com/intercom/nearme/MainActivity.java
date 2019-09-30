package com.intercom.nearme;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intercom.nearme.viewmodel.CustomerListAdapter;
import com.intercom.nearme.viewmodel.MainViewModel;
import com.intercom.nearme.viewmodel.NoDataFoundInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NoDataFoundInterface {

    private MainViewModel mainViewModel;
    @BindView(R.id.tvNoData)
    public TextView tvNoData;
    @BindView(R.id.etDistance)
    public EditText etDistance;
    @BindView(R.id.rvCustomerList)
    public RecyclerView rvCustomerList;
    private CustomerListAdapter customerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.readCustomerDetails();
        rvCustomerList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        etDistance.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.checkValidInput(etDistance.getText().toString());
            }
            return false;
        });

        mainViewModel.getOriginalCustomerList().observe(this, customers -> {
            customerListAdapter = new CustomerListAdapter(customers,this);
            rvCustomerList.setAdapter(customerListAdapter);
            //Toast.makeText(this,"Loaded "+customers.isEmpty(),Toast.LENGTH_SHORT).show();
            etDistance.setVisibility(customers.isEmpty() ? View.GONE : View.VISIBLE);
            mainViewModel.checkForCustomerList(customers.size());
        });

        mainViewModel.getIsValidInput().observe(this, isValid -> {
            if (isValid) {
                etDistance.clearFocus();
                etDistance.setError(null);
            } else {
                etDistance.setError("Invalid input");
                //etDistance.requestFocus();
            }
            isDataFound(isValid);

        });

        mainViewModel.getValidInputString().observe(this, validInputString -> {
            customerListAdapter.getFilter().filter(validInputString);
        });
    }

    @Override
    public void isDataFound(boolean dataFound) {
        tvNoData.setVisibility(dataFound ? View.GONE : View.VISIBLE);
    }
}
