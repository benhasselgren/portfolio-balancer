package com.example.portfoliobalancer.main_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.util.List;

//######################-----------------------------PortfoliosAdapterClass-----------------------------######################
//Binds the data from the PortfolioActivity to the elements in the recyclerview

public class PortfoliosAdapter extends RecyclerView.Adapter<PortfoliosHolder>
{
    //-----------------------------Variables-----------------------------
    private List<Portfolio> portfolios;
    private Context context;
    private int itemResource;

    //-----------------------------Constructor-----------------------------
    public PortfoliosAdapter(Context context, int itemResource, List<Portfolio> portfolios)
    {
        this.portfolios = portfolios;
        this.context = context;
        this.itemResource = itemResource;
    }

    //-----------------------------Methods-----------------------------

    //Passes the data from the activity to the holder
    @Override
    public PortfoliosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new PortfoliosHolder(this.context, view);
    }

    //Binds the data to the holder
    @Override
    public void onBindViewHolder(PortfoliosHolder holder, int position) {

        //set animation
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        // Use position to access the correct portfolio object
        Portfolio p = this.portfolios.get(position);

        // Bind the portfolio object to the holder
        holder.bindPortfolio(p);
    }

    //Returns the size of the list
    @Override
    public int getItemCount() {
        return this.portfolios.size();
    }
}
