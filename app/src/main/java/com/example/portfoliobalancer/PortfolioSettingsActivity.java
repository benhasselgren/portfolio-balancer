package com.example.portfoliobalancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portfoliobalancer.classes.Portfolio;
import com.example.portfoliobalancer.classes.Validation;

import java.util.Calendar;
import java.util.Date;

//######################-----------------------------PortfoliosSettingsActivityClass-----------------------------######################
//XML file: activity_portfolio_settings.xml
//Portfolio settings page of app. It's where the user can edit their portfolio details and change the target percentages.
//Also where the user is directed when creating a new portfolio
public class PortfolioSettingsActivity extends AppCompatActivity  {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    Validation validation = new Validation();
    //Views
    private RecyclerView portfoliosTargetPercentagesListView;
    private TextView totalPercentage;
    private EditText name;
    private EditText description;
    private EditText amount;
    private Button rebalance_create_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_settings);

        //Prevents keyboard from popping up intially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
        //Get the previous activity to customise activity to suit the action
        String previousActivity= getIntent().getStringExtra("FROM_ACTIVITY");

        //Add views
        portfoliosTargetPercentagesListView = (RecyclerView)findViewById(R.id.portfolios_target_percentages_list);
        totalPercentage = (TextView)findViewById(R.id.portfolio_target_percentage_total);
        name = (EditText)findViewById(R.id.portfolio_name_input);
        description = (EditText)findViewById(R.id.portfolio_description_input);
        amount = (EditText)findViewById(R.id.portfolio_amount_input);
        rebalance_create_btn = (Button) findViewById(R.id.rebalance_create__btn);

        //If the recyclerview doesn't change size, we can set this true and
        portfoliosTargetPercentagesListView.setHasFixedSize(true);

        //Initialize the PortfoliosSettings adapter, which binds the data to the entry view
        PortfoliosSettingsAdapter adapter = new PortfoliosSettingsAdapter(this, R.layout.settings_target_percentage_entry, portfolio.getCompanies());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        portfoliosTargetPercentagesListView.setLayoutManager(layoutManager);

        portfoliosTargetPercentagesListView.setAdapter(adapter);

        //Set portfolio details
        name.setText(portfolio.getName());
        description.setText(portfolio.getDescription());
        amount.setText(String.format("%.2f", portfolio.getCurrentPrice()));

        //Set portfolio target percentage total
        totalPercentage.setText(String.format("%s%%", portfolio.getTotalPercentage()));

        //Set the button text based on the previous activity
        if (previousActivity.equals("add_company"))
        {
            rebalance_create_btn.setText("Create portfolio");
        }

        //Triggers if rebalance/create button is clicked
        rebalance_create_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Set the total percentage text to show total percentage
                totalPercentage.setText(String.format("%s%%", portfolio.getTotalPercentage()));

                try
                {
                    //-------------------Check to see details are valid-------------------
                    //Convert name and description to strings
                    String nameString = name.getText().toString().trim();
                    String descriptionString = description.getText().toString().trim();
                    //Convert amount to string, then int
                    String amountString = amount.getText().toString().trim();
                    //Pass strings to validation method
                    validation.checkPortfolioDetailsValid(nameString, descriptionString, amountString);

                    //-------------------See if total of target percentages is equal too 100% -------------------
                    //If the total percentage is = 100 then save target percentages and go back to main activity
                    if(portfolio.getTotalPercentage() == 100)
                    {
                        //Add all the values to the new companies and portfolio and create portfolio
                        finalisePortfolio();
                        Intent intent = new Intent(PortfolioSettingsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("portfolio", portfolio);
                        startActivity(intent);
                    }
                    else{
                        //Throw error message if total pecentage does not equal 100%
                        throw new RuntimeException("Percentage total has to equal 100%");
                    }
                }
                catch (RuntimeException ex)
                {
                    //Catches all exceptions here and displays appropriate error message
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finalisePortfolio()
    {
        //Set the portfolio date and call the balance method here

        //Set date to now
        Date date = Calendar.getInstance().getTime();
        portfolio.setCurrentPriceDate(date);

        //Balance portfolio
        portfolio.setLastRebalanced(date);
    }
}
