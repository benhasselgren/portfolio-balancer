package com.example.portfoliobalancer.main_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity;
import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.companies_prices_activity.CompaniesPricesActivity;

import java.util.ArrayList;

/**
 * MainActivity
 * Main page of app. It shows the user all their portfolios.
 * XML file: activity_main.xml
 */

public class MainActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private UserData userData;
    private Context context;
    //Views
    private Button add_portfolio_btn;
    private Button companies_btn;
    private RecyclerView portfoliosListView;

    //-----------------------------On Create method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set context
        context = getApplicationContext();

        //Create a new UserData object
        userData = new UserData();

        //################# Create companies here
        //createCompanies();

        // Load the PORTFTOLIOS (hence parameter passed is TRUE)
        userData.loadUserData(context, true);

        if(userData.getPortfolios() != null || userData.getPortfolios().size() == 0)
        {
            //Is portfolios exist then check they are still balanced
            if(userData.getPortfolios().size() > 0)
            {
                // Load the COMPANIES (hence parameter passed is FALSE)
                userData.loadUserData(context, false);
                ArrayList<Company> updatedCompanies = (ArrayList<Company>) userData.getCompanies();
                userData.checkPortfoliosAreBalanced(updatedCompanies);
            }

            //Add views
            add_portfolio_btn = (Button)findViewById(R.id.add_portfolio_btn);
            companies_btn = (Button)findViewById(R.id.companies_activity_btn);

            portfoliosListView = (RecyclerView)findViewById(R.id.portfolios_list);

            //If the recyclerview doesn't change size, we can set this true and
            portfoliosListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            PortfoliosAdapter adapter = new PortfoliosAdapter(this, R.layout.portfolio_entry, userData.getPortfolios());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            portfoliosListView.setLayoutManager(layoutManager);

            portfoliosListView.setAdapter(adapter);

            //-----------------------------Event Listener Methods-----------------------------

            /**
             * add_portfolio_btn.setOnClickListener()
             * Triggers if add_portfolio_btn clicked
             * The user will be directed to the AddPortfolioActivity
             * @see com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity
             */
            add_portfolio_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddPortfolioActivity.class);
                    intent.putExtra("NEW_PORTFOLIO_ID", String.format("%s",userData.getPortfolios().size() + 1));
                    startActivity(intent);
                }
            });

            /**
             * companies_btn.setOnClickListener()
             * Triggers if companies_btn clicked
             * The user will be directed to the CompaniesPricesActivity
             * @see com.example.portfoliobalancer.companies_prices_activity.CompaniesPricesActivity
             */
            companies_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CompaniesPricesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NO_ANIMATION );
                    startActivity(intent);
                }
            });
        }
    }

    //-----------------------------Methods----------------------------
    private void createCompanies()
    {
        userData.createCompanies();
        userData.saveUserData(context, false);
    }
}
