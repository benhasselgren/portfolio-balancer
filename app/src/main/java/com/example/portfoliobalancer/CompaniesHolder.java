package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

public class CompaniesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views


    //-----------------------------Constructor-----------------------------
    public CompaniesHolder(Context context, View itemView)
    {
        super(itemView);

        //Set context
        this.context = context;
    }

    public void bindPortfolio(Company company)
    {
        // Bind the data to all the ViewHolders
        this.company = company;

    }

    //-----------------------------Event Listener Methods----------------------------
    //Triggered when a user clicks a portfolio
    @Override
    public void onClick(View v) {
        if (company != null) {
            Intent intent = new Intent(itemView.getContext(), CompanyDetails.class);
            intent.putExtra("company", company);
            itemView.getContext().startActivity(intent);
        }
    }
}
