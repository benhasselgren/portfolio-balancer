package com.example.portfoliobalancer.company_details_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;

/**
 * CompanyDetailsActivity
 * Displays the company details (List of companies, prices, growth, etc.)
 * XML file: activity_company_details.xml
 */

public class CompanyDetailsActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Context context;
    //Views
    private TextView name;
    private TextView price;
    private TextView growth;
    private TextView targetPercentage;
    private TextView units;

    //-----------------------------On Create Method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        //Set the context
        context = getApplicationContext();

        //Assign the intent parcelable extra to a variable
        Company company = (Company) getIntent().getParcelableExtra("company");

        //Add views
        name = (TextView) findViewById(R.id.company_details_name);
        price = (TextView) findViewById(R.id.company_details_price);
        growth = (TextView) findViewById(R.id.company_details_growth);
        targetPercentage = (TextView) findViewById(R.id.company_details_target_percentage);
        units = (TextView) findViewById(R.id.company_details_units);

        //Name field
        name.setText(company.getName());

        //Price field
        price.setText(String.format("£%.2f", company.getCostPrice()));

        //Growth field
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

        //Target percentage field
        targetPercentage.setText(String.format("%s%%", company.getTargetPercentage()));

        //Units field
        units.setText(String.format("%.2f", company.getUnitCount()));
    }
}
