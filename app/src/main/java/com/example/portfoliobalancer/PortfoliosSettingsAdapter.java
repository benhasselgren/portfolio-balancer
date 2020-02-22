package com.example.portfoliobalancer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.portfoliobalancer.classes.Company;
import java.util.List;

public class PortfoliosSettingsAdapter extends RecyclerView.Adapter<PortfoliosSettingsHolder> {

    private List<Company> companies;
    private Context context;
    private int itemResource;

    public PortfoliosSettingsAdapter(Context context, int itemResource, List<Company> companies)
    {
        this.companies = companies;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public PortfoliosSettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new PortfoliosSettingsHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(PortfoliosSettingsHolder holder, int position) {

        // Use position to access the correct place object
        Company c = this.companies.get(position);

        // Bind the place object to the holder
        holder.bindPortfolio(c);
    }


    @Override
    public int getItemCount() {
        return this.companies.size();
    }

}
