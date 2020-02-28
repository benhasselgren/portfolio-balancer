package com.example.portfoliobalancer.add_company_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.main_activity.PortfoliosHolder;
import com.example.portfoliobalancer.portfolio_details_activity.CompaniesHolder;

import java.util.List;

//######################-----------------------------AddCompanyAdapterClass-----------------------------######################
//Binds the data from the AddCompanyActivity to the elements in the recyclerview

public class AddCompanyAdapter extends RecyclerView.Adapter<AddCompanyHolder>{

    //-----------------------------Variables-----------------------------
    private List<Company> companies;
    private Context context;
    private int itemResource;

    //-----------------------------Constructor-----------------------------
    public AddCompanyAdapter(Context context, int itemResource, List<Company> companies)
    {
        this.companies = companies;
        this.context = context;
        this.itemResource = itemResource;
    }

    //-----------------------------Methods-----------------------------

    //Passes the data from the activity to the holder
    @Override
    public AddCompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new AddCompanyHolder(this.context, view);
    }

    //Binds the data to the holder
    @Override
    public void onBindViewHolder(AddCompanyHolder holder, int position) {

        // Use position to access the correct portfolio object
        Company c = this.companies.get(position);

        // Bind the portfolio object to the holder
        holder.bindAddCompany(c);
    }

    //Returns the size of the list
    @Override
    public int getItemCount() {
        return this.companies.size();
    }
}
