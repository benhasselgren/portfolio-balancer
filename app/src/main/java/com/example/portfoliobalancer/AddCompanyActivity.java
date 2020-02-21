package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.portfoliobalancer.classes.Company;
import com.example.portfoliobalancer.classes.Portfolio;

import java.util.ArrayList;

public class AddCompanyActivity extends AppCompatActivity {

    private Portfolio portfolio;
    private ArrayList<Company> availableCompanies;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        /*
        //Get the companies from the string array and add them to the companies strings array list
        String[] strings = getResources().getStringArray(R.array.companies);

        for(String s : strings)
        {

            String[] result = s.split(",");
            String code = result[0];
            String
        }


         */
    }

}
