package com.example.portfoliobalancer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

//######################-----------------------------CompanyDetailsActivity-----------------------------######################
//XML file: activity_company_details.xml
//Displays the company details (List of companies, prices, growth, etc.)

public class CompanyDetails extends AppCompatActivity {

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
        Portfolio portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
    }
}
