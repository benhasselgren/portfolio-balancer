package com.example.portfoliobalancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.util.ArrayList;

//######################-----------------------------AppCompanyActivityClass-----------------------------######################
//XML file: activity_add_company.xml
//User can add companies from a list to his portfolio

public class AddCompanyActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    private ArrayList<Company> availableCompanies;
    private ArrayList<Company> selectedCompanies;
    private ArrayList<String> companyTitles;
    private ArrayList<String> selectedCompanyTitles;
    //Views
    private AutoCompleteTextView companySelector;
    private ListView selectedCompaniesDisplay;

    //-----------------------------On Create method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        //Add views
        companySelector = (AutoCompleteTextView)findViewById(R.id.company_selector);
        selectedCompaniesDisplay = (ListView) findViewById(R.id.selected_companies);
        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_company_activity_btn);

        //Intitalise the selectedCompanies arraylist
        selectedCompanies = new ArrayList<>();
        selectedCompanyTitles = new ArrayList<>();

        //Adds the available companies from resources and adds them to array list
        getAvailableCompanies();

        ArrayAdapter<String> adapterSelector = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companyTitles);
        companySelector.setAdapter(adapterSelector);

        final ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedCompanyTitles);
        selectedCompaniesDisplay.setAdapter(adapterList);

        //-----------------------------Event Listener Methods----------------------------
        //Triggers if item is clicked
        //Adds the item clicked to the selectedCompaniesArrayList and to the selceted companies list in the xaml file
        companySelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Get company code and find company using code
                String c_code = adapterView.getItemAtPosition(i).toString();
                Company c = findCompany(c_code);

                //If company already has been selected then show error message
                if(selectedCompanies.contains(c))
                {
                    //Show error message
                    Toast.makeText(getBaseContext(), "You have already added this company.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //add to selected companies arraylist
                    selectedCompanies.add(c);
                    //Update selected companies list display and show success message
                    selectedCompanyTitles.add(c_code);
                    adapterList.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "You have succesfully added " + c.getName() + ".", Toast.LENGTH_SHORT).show();
                    //Clear search bar
                    companySelector.setText("");
                }
            }
        });

        //Triggers if next button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(selectedCompanies.size()==0)
                {
                    Toast.makeText(getBaseContext(), "You need to enter at least one company.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //If everything is valid, add companies to portfolio start a new activity (passing the portfolio to that activity)
                    portfolio.setCompanies(selectedCompanies);
                    Intent intent = new Intent(AddCompanyActivity.this, PortfolioSettingsActivity.class);
                    intent.putExtra("portfolio", portfolio);
                    intent.putExtra("FROM_ACTIVITY", "add_company");
                    startActivity(intent);
                }
            }
        });
    }

    //-----------------------------Methods-----------------------------

    //Gets all the available companies from the strings.xml file in resources
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

            //Create a new company with the initial details
            Company c = createCompany(c_name, c_code, c_price);

            //Add company object to companies list and add company title to companyTitles list
            companyTitles.add(String.format("%s", c_code));
            availableCompanies.add(c);
        }
    }

    //Finds a company in the available companies list using a company code
    private Company findCompany(String c_code)
    {
        for(Company c: availableCompanies)
        {
            if(c.getCompanyCode().equals(c_code))
            {
                return c;
            }
        }
        return null;
    }

    //Creates a company with basic details
    private Company createCompany(String c_name, String c_code, Double c_price)
    {
        Company c = new Company();
        c.setName(c_name);
        c.setCompanyCode(c_code);
        c.setCostPrice(c_price);

        return c;
    }
}
