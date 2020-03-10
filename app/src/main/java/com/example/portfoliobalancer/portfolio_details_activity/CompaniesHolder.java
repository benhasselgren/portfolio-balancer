package com.example.portfoliobalancer.portfolio_details_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.portfoliobalancer.company_details_activity.CompanyDetailsActivity;
import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;

/**
 * CompaniesHolder
 * This displays the companies
 * XML file: company_entry.xml
 */

public class CompaniesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views
    private final TextView code;
    private final TextView targetPercentage;
    private final TextView companyName;
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
        currentPrice = (TextView) itemView.findViewById(R.id.entry_company_currentPrice);
        growth = (TextView) itemView.findViewById(R.id.entry_company_growth);


        // Set the "onClick" listener of the holder
        // here we use (this) because this class has a onClick function thanks
        // to implementing the PlacesHolder class with the
        // View.OnClickListener interface
        itemView.setOnClickListener(this);
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

        // Current price
        currentPrice.setText(String.format(String.format("£%.2f", company.getCurrentUnitPrice())));

        // Company growth
        // Overall growth field
        if(Math.round(company.getPriceGrowth() * 100.0) > 0)
        {
            growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
            growth.setText(String.format("+£%.2f(+%.2f%%)", company.getPriceGrowth(), company.getPercentageGrowth()));
        }
        else if (Math.round(company.getPriceGrowth() * 100.0) < 0)
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

    /**
     * onClick()
     * Triggers if company list item is clicked
     * User is directed to CompanyDetailsActivity
     * @see com.example.portfoliobalancer.company_details_activity.CompanyDetailsActivity
     */
    @Override
    public void onClick(View v) {
        if (company != null) {
            Intent intent = new Intent(itemView.getContext(), CompanyDetailsActivity.class);
            intent.putExtra("company", company);
            itemView.getContext().startActivity(intent);
        }
    }
}
