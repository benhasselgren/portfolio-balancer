package com.example.portfoliobalancer.main_activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private RecyclerView portfoliosListView;
    private ActionBar toolbar;

    //-----------------------------On Create method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create navigation bar
        //Reference https://www.youtube.com/watch?v=2LtObBTF9CM
        toolbar = getSupportActionBar();
        toolbar.setTitle("Portfolios");

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Set context
        context = getApplicationContext();

        //Create a new UserData object
        userData = new UserData();

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
            portfoliosListView = (RecyclerView)findViewById(R.id.portfolios_list);

            //If the recyclerview doesn't change size, we can set this true and
            portfoliosListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            PortfoliosAdapter adapter = new PortfoliosAdapter(this, R.layout.portfolio_entry, userData.getPortfolios());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            portfoliosListView.setLayoutManager(layoutManager);

            portfoliosListView.setAdapter(adapter);
        }
    }

    /**
     * mOnNavigationItemSelectedListener
     * Handles bottom navigation bar clicks.
     * Reference https://www.youtube.com/watch?v=2LtObBTF9CM
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_menu:
                    return true;
                case R.id.companies_menu:
                    /**
                     * The user will be directed to the CompaniesPricesActivity
                     * @see com.example.portfoliobalancer.companies_prices_activity.CompaniesPricesActivity
                     */
                    Intent intent = new Intent(MainActivity.this, CompaniesPricesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NO_ANIMATION );
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    /**
     * onCreateOptionsMenu
     * create an action bar button
     * Reference https://stackoverflow.com/questions/38158953/how-to-create-button-in-action-bar-in-android
     * @param menu the menu that contains the add button
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemSelected
     * handle button activities
     * Reference https://stackoverflow.com/questions/38158953/how-to-create-button-in-action-bar-in-android
     * @param item the item in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            /**
             * The user will be directed to the AddPortfolioActivity
             * @see com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity
             */
            Intent intent = new Intent(MainActivity.this, AddPortfolioActivity.class);
            //if list exists then id equal to the last item in the list + 1. If list is empty, then id = 1;
            if(userData.getPortfolios().size()!=0)
            {
                intent.putExtra("NEW_PORTFOLIO_ID", String.format("%s",(userData.getPortfolios().get(userData.getPortfolios().size() - 1).getId() + 1)));
            }
            else
            {
                intent.putExtra("NEW_PORTFOLIO_ID", "1");
            }

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
