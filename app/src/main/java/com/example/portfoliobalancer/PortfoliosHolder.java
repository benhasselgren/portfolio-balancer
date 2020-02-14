package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PortfoliosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView portfolioName;

    private Portfolio portfolio;
    private Context context;

    public PortfoliosHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        this.portfolioName = (TextView) itemView.findViewById(R.id.entry_location_name);

        // Set the "onClick" listener of the holder
        // here we use (this) because this class has a onClick function thanks
        // to implementing the PlacesHolder class with the
        // View.OnClickListener interface
        itemView.setOnClickListener(this);
    }

    public void bindPortfolio(Portfolio portfolio) {
        // Bind the data to the ViewHolder
        this.portfolio = portfolio;
        this.portfolioName.setText(portfolio.getName());
    }

    @Override
    public void onClick(View v) {
        if (this.portfolio != null) {
            Intent intent = new Intent(itemView.getContext(), PortfolioDetails.class);
            intent.putExtra("portfolio", this.portfolio);
            itemView.getContext().startActivity(intent);
        }
    }
}
