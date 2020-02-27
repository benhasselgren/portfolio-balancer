package com.example.portfoliobalancer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portfoliobalancer.background_tasks.BalanceTask;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.business_logic_classes.Validation;

//######################-----------------------------PortfoliosSettingsActivityClass-----------------------------######################
//XML file: activity_portfolio_settings.xml
//Portfolio settings page of app. It's where the user can edit their portfolio details and change the target percentages.
//Also where the user is directed when creating a new portfolio
public class PortfolioSettingsActivity extends AppCompatActivity  {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    Validation validation = new Validation();
    private  String previousActivity;
    private BalanceTask balanceTask;
    //Views
    private RecyclerView portfoliosTargetPercentagesListView;
    private TextView totalPercentage;
    private EditText name;
    private EditText description;
    private EditText amount;
    private Button rebalance_create_btn;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_settings);

        //Prevents keyboard from popping up intially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
        //Get the previous activity to customise activity to suit the action
        previousActivity= getIntent().getStringExtra("FROM_ACTIVITY");

        //Add views
        portfoliosTargetPercentagesListView = (RecyclerView)findViewById(R.id.portfolios_target_percentages_list);
        totalPercentage = (TextView)findViewById(R.id.portfolio_target_percentage_total);

        name = (EditText)findViewById(R.id.portfolio_name_input);

        //Set filter
        name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(15)
        });

        // Programmatically enable the EditText to sentence first letter capitalization
        name.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );

        description = (EditText)findViewById(R.id.portfolio_description_input);
        //set description filter
        description.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30)
        });
        // Programmatically enable the EditText to sentence first letter capitalization
        description.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );

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

        //Set portfolio target percentage total
        totalPercentage.setText(String.format("%s%%", portfolio.getTotalPercentage()));

        //Set the button text based on the previous activity
        if (previousActivity.equals("add_company"))
        {
            rebalance_create_btn.setText("Create portfolio");
            amount.setText(String.format("%.2f", portfolio.getCurrentPrice(true)));
        }
        else if (previousActivity.equals("portfolio_details"))
        {
            rebalance_create_btn.setText("Update portfolio");
            amount.setText(String.format("%.2f", portfolio.getCurrentPrice(false)));
        }

        //-----------------------------EventListenerMethods-----------------------------
        //Triggers if rebalance/create button is clicked
        rebalance_create_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Set the total percentage text to show total percentage
                totalPercentage.setText(String.format("%s%%", portfolio.getTotalPercentage()));

                try
                {
                    //-----------Check to see details are valid-----------
                    //Convert name and description to strings
                    String nameString = name.getText().toString().trim();
                    String descriptionString = description.getText().toString().trim();
                    //Convert amount to string then split to get the left side of the decimal
                    String amountString = amount.getText().toString();
                    String[] amountStringSplit = amountString.split("\\.");
                    //Pass strings to validation method
                    validation.checkPortfolioDetailsValid(nameString, descriptionString, amountStringSplit[0]);

                    //-----------See if total of target percentages is equal too 100%-----------
                    //If the total percentage is = 100 then save target percentages and go back to main activity
                    if(portfolio.getTotalPercentage() == 100)
                    {
                        //Add all the values to the new companies and portfolio and create portfolio
                        finalisePortfolio(nameString, descriptionString, amountString);
                        Intent intent = new Intent(PortfolioSettingsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    //-----------------------------Methods-----------------------------

    //Adds the remaining values tot he empty variables in the object
    private void finalisePortfolio(String nameString, String descriptionString, String amountString)
    {
        //Update porfolio details
        portfolio.setName(nameString);
        portfolio.setDescription(descriptionString);
        portfolio.setInitialPrice(Double.parseDouble(amountString));
        portfolio.setCurrentPrice(Double.parseDouble(amountString));

        // Initialize the progress dialog
        progressDialog = new ProgressDialog(PortfolioSettingsActivity.this);
        progressDialog.setIndeterminate(true);
        // Progress dialog horizontal style
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress dialog title
        progressDialog.setTitle( getResources().getString(R.string.balance_dialog_title));
        // Progress dialog message
        progressDialog.setMessage(getResources().getString(R.string.balance_dialog_message));

        //Start the process dialog
        progressDialog.show();

        //Balance portfolio
        //Get previous activity to decide how to balance the portfolio
        if (previousActivity.equals("add_company"))
        {
            portfolio.balancePortfolio(true);
        }
        else if (previousActivity.equals("portfolio_details"))
        {
            portfolio.balancePortfolio(false);
        }

        //Load portfolios, check if this portfolio exists and add or update portfolio, then save portfolios
        //Load
        UserData ud = new UserData();
        ud.loadUserData(getApplicationContext());

        //Check if it exists
        Portfolio p = ud.findPortfolioById(portfolio.getId());

        if (p != null)
        {
            //If it does then update
            ud.updatePortfolio(portfolio, p);
        }
        else
        {
            //If it doesn't then add it to portfolios
            ud.addPortfolio(portfolio);
        }

        //Save portfolios
        ud.saveUserData(getApplicationContext());

        //End process dialog
        progressDialog.dismiss();

        /*
        // Create the async task
        balanceTask = new BalanceTask(
                progressDialog,
                getApplicationContext()
        );
        // now execute the Task and pass the portfolio to balance
        balanceTask.execute(
                portfolio
        );
        */

    }
}
