package com.example.portfoliobalancer.company_details_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    //-----------------------------On Create Method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        //Set the context
        context = getApplicationContext();

        //Assign the intent parcelable extra to a variable
        Company company = (Company) getIntent().getParcelableExtra("company");
    }
}
