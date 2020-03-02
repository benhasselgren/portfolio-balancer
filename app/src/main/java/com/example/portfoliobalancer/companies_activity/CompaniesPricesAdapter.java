package com.example.portfoliobalancer.companies_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.portfoliobalancer.add_company_activity.AddCompanyHolder;
import com.example.portfoliobalancer.business_logic_classes.Company;

import java.util.List;

/**
 * CompaniesPricesAdapter
 * Binds the data from the CompaniesPricesActivity to the elements in the recyclerview
 */


public class CompaniesPricesAdapter extends RecyclerView.Adapter<CompaniesPricesHolder>{

    //-----------------------------Variables-----------------------------
    private List<Company> companies;
    private Context context;
    private int itemResource;

    //-----------------------------Constructor-----------------------------
    public CompaniesPricesAdapter(Context context, int itemResource, List<Company> companies)
    {
        this.companies = companies;
        this.context = context;
        this.itemResource = itemResource;
    }

    //-----------------------------Methods-----------------------------

    /**
     * onCreateViewHolder()
     * @param parent
     * @param viewType
     * @return view holder
     */
    @Override
    public AddCompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new AddCompanyHolder(this.context, view);
    }

    /**
     * onBindViewHolder()
     * Binds data to the view holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AddCompanyHolder holder, int position) {

        // Use position to access the correct portfolio object
        Company c = this.companies.get(position);

        // Bind the portfolio object to the holder
        holder.bindAddCompany(c);
    }

    /**
     * getItemCount()
     * @return the size of the array list
     */
    @Override
    public int getItemCount() {
        return this.companies.size();
    }
}
