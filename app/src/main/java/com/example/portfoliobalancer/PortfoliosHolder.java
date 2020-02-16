package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;

public class PortfoliosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView name;
    private final TextView description;
    private final TextView last_balanced;
    private final TextView unbalanced;
    private final TextView currentPrice;
    private final TextView growth;

    private Portfolio portfolio;
    private Context context;

    public PortfoliosHolder(Context context, View itemView) {
        super(itemView);

        //Set context
        this.context = context;

        //Assign existing views to variables
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

        // ------------------- Last rebalanced Field -------------------
        //this.last_balanced.setText(String.format("Last rebalanced: %t", new SimpleDateFormat("MM-dd-yyyy").format(portfolio.getLastRebalanced())));

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
            this.growth.setTextColor(Color.parseColor("#34AAF1"));
            this.growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else if (portfolio.getCurrentPrice() < portfolio.getInitialPrice())
        {
            this.growth.setTextColor(Color.parseColor("#C52222"));
            this.growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else {
            this.growth.setTextColor(Color.parseColor("#31c533"));
            this.growth.setText("£00.00(0.0%)");
        }
    }

    @Override
    public void onClick(View v) {
        if (this.portfolio != null) {
            Intent intent = new Intent(itemView.getContext(), PortfolioDetails.class);
            intent.putExtra("portfolio", this.portfolio);
            itemView.getContext().startActivity(intent);
        }
    }

    protected void displayMemoryUsage(String message) {
        int usedKBytes = (int) (Debug.getNativeHeapAllocatedSize() / 1024L);
        String usedMegsString = String.format("%s - usedMemory = Memory Used: %d KB", message, usedKBytes);
        Log.d("DATA", usedMegsString);
    }
}
