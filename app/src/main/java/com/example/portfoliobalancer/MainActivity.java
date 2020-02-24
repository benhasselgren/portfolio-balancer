package com.example.portfoliobalancer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;

//######################-----------------------------MainActivityClass-----------------------------######################
//XML file: activity_main.xml
//Main page of app. It shows the user all their portfolios.

public class MainActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private ArrayList<Company> companies;
    private UserData userData;
    private Context context;
    //Views
    private Button add_portfolio_btn;
    private RecyclerView portfoliosListView;

    //-----------------------------On Create method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the context
        context = getApplicationContext();

        //Create a new UserData object
        userData = new UserData();

        // ########## LOAD PORTFOLIOS HERE ##########
        userData.loadUserData(context);

        //If the user has come from the create portfolio screen then add new portfolio and save it to the device
        //Assign the intent parcelable extra to a variable
        Portfolio newPortfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
        if (newPortfolio != null)
        {
            userData.addPortfolio(newPortfolio);

            // ########## SAVE PORTFOLIOS HERE ##########
            userData.saveUserData(context);
        }

        if(userData.getPortfolios() != null )
        {
            //Add views
            add_portfolio_btn = (Button)findViewById(R.id.add_portfolio_btn);

            portfoliosListView = (RecyclerView)findViewById(R.id.portfolios_list);

            //If the recyclerview doesn't change size, we can set this true and
            portfoliosListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            PortfoliosAdapter adapter = new PortfoliosAdapter(this, R.layout.portfolio_entry, userData.getPortfolios());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            portfoliosListView.setLayoutManager(layoutManager);

            portfoliosListView.setAdapter(adapter);

            //-----------------------------Event Listener Methods-----------------------------
            //Add portfolio button clicked event handled here
            add_portfolio_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AddPortfolioActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    //-----------------------------Methods----------------------------
}
