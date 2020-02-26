package com.example.portfoliobalancer;

//######################-----------------------------CompaniesAdapterClass-----------------------------######################
//Binds the data from the PortfolioDetailsActivity to the elements in the recyclerview

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.util.List;

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

    //Passes the data from the activity to the holder
    @Override
    public CompaniesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new CompaniesHolder(this.context, view);
    }

    //Binds the data to the holder
    @Override
    public void onBindViewHolder(CompaniesHolder holder, int position) {

        // Use position to access the correct portfolio object
        Company c = this.companies.get(position);

        // Bind the portfolio object to the holder
        holder.bindCompany(c);
    }

    //Returns the size of the list
    @Override
    public int getItemCount() {
        return this.companies.size();
    }
}
