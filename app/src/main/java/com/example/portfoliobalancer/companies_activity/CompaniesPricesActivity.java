package com.example.portfoliobalancer.companies_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.main_activity.PortfoliosAdapter;

/**
 * CompaniesPricesActivity
 * Companies page of app. It shows the user all the companies they have added to the application.
 * The user can add/edit/remove companies
 * XML file: activity_main.xml
 */

public class CompaniesPricesActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private UserData userData;
    private Context context;
    //Views
    private Button add_company_btn;
    private RecyclerView companiesListView;

    //-----------------------------On Create method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_prices);

        //Set context
        context = getApplicationContext();

        //Create a new UserData object
        userData = new UserData();

        //################# Create companies here
        //createCompanies();

        // Load the COMPANIES (hence parameter passed is FALSE)
        userData.loadUserData(context, false);

        if(userData.getCompanies() != null || userData.getCompanies().size() == 0)
        {
            //Add views
            add_company_btn = (Button)findViewById(R.id.companies_add_btn);

            companiesListView = (RecyclerView)findViewById(R.id.companies_prices_list);

            //If the recyclerview doesn't change size, we can set this true and
            companiesListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            PortfoliosAdapter adapter = new CompaniesPricesAdapter(this, R.layout.portfolio_entry, userData.getPortfolios());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            companiesListView.setLayoutManager(layoutManager);

            companiesListView.setAdapter(adapter);

            //-----------------------------Event Listener Methods-----------------------------

            /**
             * nextButton.setOnClickListener()
             * Triggers if add_portfolio_btn clicked
             * The user will be directed to the AddPortfolioActivity
             * @see com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity
             */
        }
    }
}
