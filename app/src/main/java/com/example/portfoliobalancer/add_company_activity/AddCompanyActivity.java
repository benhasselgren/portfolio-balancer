package com.example.portfoliobalancer.add_company_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.portfolio_settings_activity.PortfolioSettingsActivity;
import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.util.ArrayList;

/**
 * AppCompanyActivity
 * User can add companies from a list to his portfolio
 * XML file: activity_add_company.xml
 */


public class AddCompanyActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    private ArrayList<Company> availableCompanies;
    private ArrayList<Company> selectedCompanies;
    private String previousActivity;
    //Views
    private AutoCompleteTextView companySelector;
    private RecyclerView selectedCompaniesList;

    //-----------------------------On Create method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        //Prevents keyboard from popping up intially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Get the previous activity to customise activity to suit the action
        previousActivity= getIntent().getStringExtra("FROM_ACTIVITY");

        //Use the portfolio based on the previous activity
        if (previousActivity.equals("add_portfolio"))
        {
            //Assign the intent parcelable extra to a variable
            portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
            //Intitalise the selectedCompanies arraylist
            selectedCompanies = new ArrayList<>();
        }
        else if(previousActivity.equals("portfolio_details"))
        {
            //Assign the intent parcelable extra to a variable
            portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
            //Set selected companies to the companies already in the portfolio
            selectedCompanies = (ArrayList<Company>) portfolio.getCompanies();
        }
        //Add views
        companySelector = (AutoCompleteTextView)findViewById(R.id.company_selector);
        selectedCompaniesList = (RecyclerView) findViewById(R.id.selected_companies);
        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_company_activity_btn);

        //Adds the available companies from device and adds them to array list
        getAvailableCompanies();

        //Add objects to a autocomplete text view to create a search bar
        ArrayAdapter<Company> adapterSelector = new ArrayAdapter<Company>(this, android.R.layout.simple_list_item_1, availableCompanies);
        companySelector.setAdapter(adapterSelector);

        //Create a selected companies list
        //If the recyclerview doesn't change size, we can set this true and
        selectedCompaniesList.setHasFixedSize(true);

        //Initialize the companies adapter, which binds the data to the entry view
        final AddCompanyAdapter adapter = new AddCompanyAdapter(this, R.layout.selected_company_entry, selectedCompanies);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        selectedCompaniesList.setLayoutManager(layoutManager);

        selectedCompaniesList.setAdapter(adapter);

        //-----------------------------Event Listener Methods----------------------------

        /**
         * companySelector.setOnItemClickListener()
         * Triggers if item is company is clicked in autoCompleteTextView
         * Adds the company clicked to the selectedCompaniesArrayList and to the selceted companies recylerview in the xaml file
         */
        companySelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Get company code and find company using code
                Company c = (Company) adapterView.getItemAtPosition(i);

                //If company already has been selected then show error message
                if(checkIfAlreadySelected(c))
                {
                    //Show error message
                    Toast.makeText(getBaseContext(), "You have already added this company.", Toast.LENGTH_SHORT).show();
                    //Clear search bar
                    companySelector.setText("");
                }
                else
                {
                    //Set the intital price of the company to the current costPrice
                    c.setInitialPrice(c.getCostPrice());
                    //add to selected companies arraylist
                    selectedCompanies.add(c);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "You have succesfully added " + c.getName() + ".", Toast.LENGTH_SHORT).show();
                    //Clear search bar
                    companySelector.setText("");
                }
            }
        });

        /**
         * nextButton.setOnClickListener()
         * Triggers if next button is clicked
         * If at least one company has been selected then the user will be directed to the next activity (PortfolioSettingsActivity)
         * @see com.example.portfoliobalancer.portfolio_settings_activity.PortfolioSettingsActivity
         */
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
                    //If previous activity was portfolio details then pass that information to portfolio settings activity so it..
                    //..knows how to balance portfolio properly
                    if(previousActivity.equals("portfolio_details"))
                    {
                        intent.putExtra("FROM_ACTIVITY", "portfolio_details");
                    }
                    else
                    {
                        intent.putExtra("FROM_ACTIVITY", "add_company");
                    }
                    startActivity(intent);
                }
            }
        });
    }

    //-----------------------------Methods-----------------------------

    /**
     * getAvailableCompanies()
     * Loads all the companies from device and adds them to the available companies array list.
     * Uses the load method from the UserData class
     * @see com.example.portfoliobalancer.business_logic_classes.UserData
     */
    private void getAvailableCompanies()
    {

        //Get the companies from the device
        UserData ud = new UserData();
        ud.loadUserData(getApplicationContext(), false);

        availableCompanies = (ArrayList<Company>) ud.getCompanies();
    }

    /**
     * checkIfAlreadySelected()
     * Checks company hasn't already been selected
     * @param company
     * @return True or false depending on if a company has been found
     */
    private boolean checkIfAlreadySelected(Company company)
    {
        for(Company c : selectedCompanies)
        {
            if(c.getCompanyCode().equals(company.getCompanyCode()))
            {
                return true;
            }
        }
        return false;
    }
}
