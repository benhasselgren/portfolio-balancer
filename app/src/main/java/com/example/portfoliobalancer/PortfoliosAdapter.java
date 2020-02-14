package com.example.portfoliobalancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PortfoliosAdapter extends RecyclerView.Adapter<PortfoliosHolder>
{

    private final List<Portfolio> portfolios;
    private Context context;
    private int itemResource;

    public PortfoliosAdapter(Context context, int itemResource, List<Portfolio> portfolios)
    {
        this.portfolios = portfolios;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public PortfoliosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new PortfoliosHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(PortfoliosHolder holder, int position) {

        // Use position to access the correct place object
        Portfolio p = this.portfolios.get(position);

        // Bind the place object to the holder
        holder.bindPortfolio(p);
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
