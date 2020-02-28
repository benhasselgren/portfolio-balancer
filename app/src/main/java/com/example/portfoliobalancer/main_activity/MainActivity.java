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

import java.util.ArrayList;

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

        //Set context
        context = getApplicationContext();

        //SharedPreferences pref = getApplicationContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
       // Editor editor = pref.edit();

        //editor.clear();
        //editor.commit();

        //Create a new UserData object
        userData = new UserData();

        //Create companies
        createCompanies();

        userData.loadUserData(context, true);

        if(userData.getPortfolios() != null || userData.getPortfolios().size() == 0)
        {
            //Is portfolios exist then check they are still balanced
            if(userData.getPortfolios().size() > 0)
            {
                userData.checkPortfoliosAreBalanced(getResources().getStringArray(R.array.companies));
            }

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
                    intent.putExtra("NEW_PORTFOLIO_ID", String.format("%s",userData.getPortfolios().size() + 1));
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
