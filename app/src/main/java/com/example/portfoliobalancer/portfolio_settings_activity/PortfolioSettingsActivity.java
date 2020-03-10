package com.example.portfoliobalancer.portfolio_settings_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.business_logic_classes.Validation;
import com.example.portfoliobalancer.main_activity.MainActivity;

/**
 * PortfoliosSettingsActivity
 * Portfolio settings page of app. It's where the user can edit their portfolio details and change the target percentages.
 * Also where they can delete companies and also where they can delete their portfolio.
 * XML file: activity_portfolio_settings.xml
 */
public class PortfolioSettingsActivity extends AppCompatActivity implements PortfoliosSettingsAdapter.OnCompanyDeleted {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Portfolio portfolio;
    Validation validation = new Validation();
    private String previousActivity;
    //Views
    private RecyclerView portfoliosTargetPercentagesListView;
    private TextView totalPercentage;
    private EditText name;
    private EditText description;
    private EditText amount;
    private Button rebalanceCreateBtn;
    private Button deletePortfolioBtn;

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
        rebalanceCreateBtn = (Button) findViewById(R.id.rebalance_create__btn);
        deletePortfolioBtn = (Button) findViewById(R.id.portfolio_delete_btn);

        //If the recyclerview doesn't change size, we can set this true and
        portfoliosTargetPercentagesListView.setHasFixedSize(true);

        //Initialize the PortfoliosSettings adapter, which binds the data to the entry view
        PortfoliosSettingsAdapter adapter = new PortfoliosSettingsAdapter(this, R.layout.settings_target_percentage_entry, portfolio.getCompanies(), this);

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
            rebalanceCreateBtn.setText("Create portfolio");
            amount.setText(String.format("%.2f", portfolio.getCurrentPrice(true)));
        }
        else if (previousActivity.equals("portfolio_details"))
        {
            rebalanceCreateBtn.setText("Update portfolio");
            amount.setText(String.format("%.2f", portfolio.getCurrentPrice(false)));
        }

        //-----------------------------EventListenerMethods-----------------------------

        /**
         * rebalanceCreateBtn.setOnClickListener()
         * Triggers if rebalanceCreateBtn clicked
         * The portfolio will be rebalanced
         * Uses the the balancePortfolio method from Portfolio class
         * Once balanced the user will be sent back to the Main activity
         * @see com.example.portfoliobalancer.business_logic_classes.Portfolio
         * @see com.example.portfoliobalancer.main_activity.MainActivity
         */
        rebalanceCreateBtn.setOnClickListener(new View.OnClickListener() {
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

        /**
         * deletePortfolioBtn.setOnClickListener()
         * Triggers if deletePortfolioBtn clicked
         * The portfolio will be deleted and the user will be directed to the MainActivity
         * @see com.example.portfoliobalancer.main_activity.MainActivity
         */
        deletePortfolioBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserData ud = new UserData();

                //Load the portfolios
                ud.loadUserData(getApplicationContext(), true);

                //Find the portfolio
                Portfolio p = ud.findPortfolioById(portfolio.getId());

                //Delete the portfolio
                ud.removePortfolio(p);

                //Save the portfolios
                ud.saveUserData(getApplicationContext(), true);

                //Send user back to main activity
                Intent intent = new Intent(PortfolioSettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    //-----------------------------Methods-----------------------------

    /**
     * finalisePortfolio()
     * Finalises the portfolio before saving it to the device
     * @param nameString
     * @param descriptionString
     * @param amountString
     */
    private void finalisePortfolio(String nameString, String descriptionString, String amountString)
    {
        //Update porfolio details
        portfolio.setName(nameString);
        portfolio.setDescription(descriptionString);
        //Only update initial price if the user is creating a new portfolio (if the previous activity tag equals "add_company" then it's a new portfolio)
        if (previousActivity.equals("add_company"))
        {
            portfolio.setInitialPrice(Double.parseDouble(amountString));
        }

        //Balance portfolio
        //Get previous activity to decide how to balance the portfolio
        if (previousActivity.equals("add_company"))
        {
            //If previous activity = "add_company" then we know it's a new company, so pass true to portfolioBalancer method, with 0 added and rmeoved to portfolio
            portfolio.balancePortfolio(true, 0);
        }
        else if (previousActivity.equals("portfolio_details"))
        {
            //If previous activity = "portfolio_details" then we know it's not a new company, so pass false to portfolioBalancer method, and pass the amount to check..
            //..money has been removed or added.
            portfolio.balancePortfolio(false, Double.parseDouble(amountString));
        }

        //Load portfolios, check if this portfolio exists and add or update portfolio, then save portfolios
        //Load
        UserData ud = new UserData();
        ud.loadUserData(getApplicationContext(), true);

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
        ud.saveUserData(getApplicationContext(), true);
    }

    /**
     * onDeleteCompanyClick()
     * Triggered by a callback in PortfolioSettingsAdapter
     * If company is removed then update the totalAmountAdded (totalAmountAdded - currentUnitPrice of company being deleted).
     * This is to prevent the growth percentage values being affected.
     * @param value
     */
    @Override
    public void onDeleteCompanyClick(double value) {
        portfolio.removeAmountFromTotalAmountAdded(value);
    }
}
