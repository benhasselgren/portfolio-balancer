package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import java.util.prefs.Preferences;

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

       // SharedPreferences pref = getApplicationContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
       // Editor editor = pref.edit();

       // editor.clear();
        //editor.commit();

        //Create a new UserData object
        userData = new UserData();

        userData.loadUserData(context);

        userData.checkPortfoliosAreBalanced(getResources().getStringArray(R.array.companies));

        if(userData.getPortfolios() != null || userData.getPortfolios().size() == 0)
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
                    intent.putExtra("NEW_PORTFOLIO_ID", String.format("%s",userData.getPortfolios().size() + 1));
                    startActivity(intent);
                }
            });
        }
    }

    //-----------------------------Methods----------------------------
}
