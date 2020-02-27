package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Company;

public class CompaniesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views
    private final TextView code;
    private final TextView targetPercentage;
    private final TextView companyName;
    private final TextView percentageChange;
    private final TextView currentPrice;
    private final TextView growth;

    //-----------------------------Constructor-----------------------------
    public CompaniesHolder(Context context, View itemView)
    {
        super(itemView);

        //Set context
        this.context = context;

        //Add views
        code = (TextView) itemView.findViewById(R.id.entry_company_code);
        targetPercentage = (TextView) itemView.findViewById(R.id.entry_company_target_percentage);
        companyName = (TextView) itemView.findViewById(R.id.entry_company_name);
        percentageChange = (TextView) itemView.findViewById(R.id.entry_company_percentageChange);
        currentPrice = (TextView) itemView.findViewById(R.id.entry_company_currentPrice);
        growth = (TextView) itemView.findViewById(R.id.entry_company_growth);
    }

    public void bindCompany(Company company)
    {
        // Bind the data to all the ViewHolders
        this.company = company;

        // Code
        code.setText(company.getCompanyCode());

        // Target percentage
        targetPercentage.setText(String.format("%s%%", company.getTargetPercentage()));

        // Name
        companyName.setText(company.getName());

        // Percentage change
        percentageChange.setText(String.format("Percentage change: %s%%", company.getTargetPercentage()));

        // Current price
        currentPrice.setText(String.format(String.format("£%.2f", company.getCurrentUnitPrice())));

        // Company growth
        // Overall growth field
        if(company.getPriceGrowth() > 0)
        {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
            growth.setText(String.format("+£%.2f(%.2f%%)", company.getPriceGrowth(), company.getPercentageGrowth()));
        }
        else if (company.getPriceGrowth() < 0)
        {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetDecline));
            growth.setText(String.format("-£%.2f(-%.2f%%)", Math.abs(company.getPriceGrowth()), Math.abs(company.getPercentageGrowth())));
        }
        else {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAsset));
            growth.setText("£00.00(0.0%)");
        }

    }

    //-----------------------------Event Listener Methods----------------------------
    //Triggered when a user clicks a company
    @Override
    public void onClick(View v) {
        if (company != null) {
            Intent intent = new Intent(itemView.getContext(), CompanyDetailsActivity.class);
            intent.putExtra("company", company);
            itemView.getContext().startActivity(intent);
        }
    }
}
