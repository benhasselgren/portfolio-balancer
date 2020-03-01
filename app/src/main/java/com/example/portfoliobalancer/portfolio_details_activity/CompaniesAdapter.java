package com.example.portfoliobalancer.portfolio_details_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.portfoliobalancer.business_logic_classes.Company;

import java.util.List;

/**
 * CompaniesAdapter
 * Binds the data from the PortfolioDetailsActivity to the elements in the recyclerview
 */

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesHolder>{

    //-----------------------------Variables-----------------------------
    private List<Company> companies;
    private Context context;
    private int itemResource;

    //-----------------------------Constructor-----------------------------
    public CompaniesAdapter(Context context, int itemResource, List<Company> companies)
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
    public CompaniesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new CompaniesHolder(this.context, view);
    }

    /**
     * onBindViewHolder()
     * Binds data to the view holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CompaniesHolder holder, int position) {

        // Use position to access the correct portfolio object
        Company c = this.companies.get(position);

        // Bind the portfolio object to the holder
        holder.bindCompany(c);
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
