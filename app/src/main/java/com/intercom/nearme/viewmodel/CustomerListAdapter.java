package com.intercom.nearme.viewmodel;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.intercom.nearme.R;
import com.intercom.nearme.databinding.CustomerItemBinding;
import com.intercom.nearme.model.Customer;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> implements Filterable {

    private ArrayList<Customer> originalCustomerList, customerList = new ArrayList<Customer>();
    private LayoutInflater layoutInflater;
    private int size = 0;
    private NoDataFoundInterface noDataFoundInterface;



    public CustomerListAdapter(ArrayList<Customer> customerList,NoDataFoundInterface noDataFoundInterface ) {
        this.originalCustomerList = customerList;
        this.noDataFoundInterface = noDataFoundInterface;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        CustomerItemBinding customerItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.customer_item, parent, false);

        return new CustomerViewHolder(customerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.customerItemBinding.setCustomer(customerList.get(position));
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                double distanceRange = Double.parseDouble(constraint.toString());
                ArrayList<Customer> filterCustomerList = new ArrayList<>();
                int size = originalCustomerList.size();
                Customer originalCustomer;
                for (int i = 0; i < size; i++) {
                    originalCustomer = originalCustomerList.get(i);
                    if (distanceRange >= originalCustomer.getDistance()) {
                        filterCustomerList.add(originalCustomer);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filterCustomerList.size();
                filterResults.values = filterCustomerList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                customerList = (ArrayList<Customer>) results.values;
                size = results.count;
                notifyDataSetChanged();
                if(size==0){
                   noDataFoundInterface.isDataFound(false);
                }else{
                    noDataFoundInterface.isDataFound(true);
                }
            }
        };

    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final CustomerItemBinding customerItemBinding;

        public CustomerViewHolder(@NonNull CustomerItemBinding customerItemBinding) {
            super(customerItemBinding.getRoot());
            this.customerItemBinding = customerItemBinding;
        }
    }
}
