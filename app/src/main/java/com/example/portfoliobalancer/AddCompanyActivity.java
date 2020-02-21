package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.portfoliobalancer.classes.Company;
import com.example.portfoliobalancer.classes.Portfolio;

import java.util.ArrayList;

public class AddCompanyActivity extends AppCompatActivity {

    private Portfolio portfolio;
    private ArrayList<Company> availableCompanies;
    private ArrayList<String> companyTitles;
    private AutoCompleteTextView companySelector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        //Add autocompletetextview
        companySelector = (AutoCompleteTextView)findViewById(R.id.company_selector);

        //Adds the available companies from resources and adds them to array list
        getAvailableCompanies();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companyTitles);
        companySelector.setAdapter(adapter);
    }

    private void getAvailableCompanies()
    {
        //Initialise variable
        availableCompanies = new ArrayList<>();
        companyTitles = new ArrayList<>();

        //Get the companies from the string array and add them to the companies strings array list
        String[] strings = getResources().getStringArray(R.array.companies);

        for(String s : strings)
        {

            String[] result = s.split(",");
            String c_code = result[0];
            String c_name = result[1];
            Double c_price = Double.parseDouble(result[2]);

            Company c = new Company(c_name, c_code, 0, c_price, 0, 0, null );

            //Add company object to companies list and add company title to companyTitles list
            companyTitles.add(String.format("%s (%s)", c_name, c_code));
            availableCompanies.add(c);
        }
    }
}
