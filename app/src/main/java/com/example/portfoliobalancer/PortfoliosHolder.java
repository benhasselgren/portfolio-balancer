package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//######################-----------------------------PortfoliosHolderClass-----------------------------######################
//XML file: portfolio_entry.xml
//This displays the portfolios

public class PortfoliosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    private Context context;
    //Views
    private final TextView name;
    private final TextView description;
    private final TextView last_balanced;
    private final TextView unbalanced;
    private final TextView currentPrice;
    private final TextView growth;
    public RelativeLayout container;


    //-----------------------------Constructor-----------------------------
    public PortfoliosHolder(Context context, View itemView) {
        super(itemView);

        //Set context
        this.context = context;

        //Set container relative layout
        container = (RelativeLayout) itemView.findViewById(R.id.container);

        //Add views
        name = (TextView) itemView.findViewById(R.id.entry_portfolio_name);
        description = (TextView) itemView.findViewById(R.id.entry_portfolio_description);
        last_balanced = (TextView) itemView.findViewById(R.id.entry_portfolio_last_rebalanced);
        unbalanced = (TextView) itemView.findViewById(R.id.entry_portfolio_unbalanced);
        currentPrice = (TextView) itemView.findViewById(R.id.entry_portfolio_currentPrice);
        growth = (TextView) itemView.findViewById(R.id.entry_portfolio_growth);

        // Set the "onClick" listener of the holder
        // here we use (this) because this class has a onClick function thanks
        // to implementing the PlacesHolder class with the
        // View.OnClickListener interface
        itemView.setOnClickListener(this);
    }

    public void bindPortfolio(Portfolio portfolio) {
        // Bind the data to all the ViewHolders
        this.portfolio = portfolio;

        // Name Field
        name.setText(portfolio.getName());

        // Description Field
        description.setText(portfolio.getDescription());

        // Last rebalanced date Field
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String date = formatter.format(portfolio.getLastRebalanced());
        last_balanced.setText(String.format("Last rebalanced: %s", date));

        // Unbalanced alert badge
        if(portfolio.isBalanced())
        {
            unbalanced.setVisibility(View.INVISIBLE);
        }

        // Current portfolio price Field
        currentPrice.setText(String.format("£%.2f", portfolio.getCurrentPrice(false)));

        // Overall growth field
        if(portfolio.getPriceGrowth() > 0)
        {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
            growth.setText(String.format("+£%.2f(%.2f%%)", portfolio.getPriceGrowth(), portfolio.getPercentageGrowth()));
        }
        else if (portfolio.getPriceGrowth() < 0)
        {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetDecline));
            growth.setText(String.format("+£%.2f(%.2f%%)", portfolio.getPriceGrowth(), portfolio.getPercentageGrowth()));
        }
        else {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAsset));
            growth.setText("£00.00(0.0%)");
        }
    }

    //-----------------------------Event Listener Methods----------------------------
    //Triggered when a user clicks a portfolio
    @Override
    public void onClick(View v) {
        if (portfolio != null) {
            Intent intent = new Intent(itemView.getContext(), PortfolioDetailsActivity.class);
            intent.putExtra("portfolio", portfolio);
            itemView.getContext().startActivity(intent);
        }
    }
/*
    protected void displayMemoryUsage(String message) {
        int usedKBytes = (int) (Debug.getNativeHeapAllocatedSize() / 1024L);
        String usedMegsString = String.format("%s - usedMemory = Memory Used: %d KB", message, usedKBytes);
        Log.d("DATA", usedMegsString);
    }

 */
}
