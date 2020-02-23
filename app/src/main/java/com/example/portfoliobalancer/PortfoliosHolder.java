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

public class PortfoliosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView name;
    private final TextView description;
    private final TextView last_balanced;
    private final TextView unbalanced;
    private final TextView currentPrice;
    private final TextView growth;

    public RelativeLayout container;
    private Portfolio portfolio;
    private Context context;

    public PortfoliosHolder(Context context, View itemView) {
        super(itemView);

        //Set context
        this.context = context;

        //Set container relative layout
        this.container = (RelativeLayout) itemView.findViewById(R.id.container);

        //Add views
        this.name = (TextView) itemView.findViewById(R.id.entry_portfolio_name);
        this.description = (TextView) itemView.findViewById(R.id.entry_portfolio_description);
        this.last_balanced = (TextView) itemView.findViewById(R.id.entry_portfolio_last_rebalanced);
        this.unbalanced = (TextView) itemView.findViewById(R.id.entry_portfolio_unbalanced);
        this.currentPrice = (TextView) itemView.findViewById(R.id.entry_portfolio_currentPrice);
        this.growth = (TextView) itemView.findViewById(R.id.entry_portfolio_growth);

        // Set the "onClick" listener of the holder
        // here we use (this) because this class has a onClick function thanks
        // to implementing the PlacesHolder class with the
        // View.OnClickListener interface
        itemView.setOnClickListener(this);
    }

    public void bindPortfolio(Portfolio portfolio) {
        // Bind the data to all the ViewHolders
        this.portfolio = portfolio;

        // ------------------- Name Field -------------------
        this.name.setText(portfolio.getName());

        // ------------------- Description Field -------------------
        this.description.setText(portfolio.getDescription());

        // ------------------- Last rebalanced date Field -------------------
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String date = formatter.format(portfolio.getLastRebalanced());
        this.last_balanced.setText(String.format("Last rebalanced: %s", date));

        // ------------------- Unbalanced alert badge  -------------------
        if(portfolio.isBalanced())
        {
            this.unbalanced.setVisibility(View.INVISIBLE);
        }

        // ------------------- Current portfolio price Field -------------------
        this.currentPrice.setText(String.format("£%.2f", portfolio.getCurrentPrice()));

        // ------------------- Overall growth field Field -------------------
        if(portfolio.getCurrentPrice() > portfolio.getInitialPrice())
        {
            this.growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
            this.growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else if (portfolio.getCurrentPrice() < portfolio.getInitialPrice())
        {
            this.growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetDecline));
            this.growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else {
            this.growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAsset));
            this.growth.setText("£00.00(0.0%)");
        }
    }

    @Override
    public void onClick(View v) {
        if (this.portfolio != null) {
            Intent intent = new Intent(itemView.getContext(), PortfolioDetailsActivity.class);
            intent.putExtra("portfolio", this.portfolio);
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
