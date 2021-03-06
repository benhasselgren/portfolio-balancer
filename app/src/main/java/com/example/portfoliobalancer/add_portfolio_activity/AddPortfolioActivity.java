package com.example.portfoliobalancer.add_portfolio_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;

import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.portfoliobalancer.add_company_activity.AddCompanyActivity;
import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.Validation;

/**
 * AppPortfolioActivity
 * User adds the portfolio name, description and amount to invest here
 * XML file: activity_add_portfolio.xml
 */

public class AddPortfolioActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Validation validation = new Validation();
    private static int PERCENTAGE_CHANGE_LIMIT = 10;
    private int portfolioId;
    //Views
    private EditText name;
    private EditText description;
    private EditText amount;

    //-----------------------------On Create method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portfolio);

        //Prevents keyboard from popping up intially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Get the portfolio id
        portfolioId= Integer.parseInt(getIntent().getStringExtra("NEW_PORTFOLIO_ID"));

        //Assigns the name field to a variable and adds validation
        name = (EditText) findViewById(R.id.add_portfolio_name_input);

        name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(15)
        });

        // Programmatically enable the EditText to sentence first letter capitalization
        name.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );

        //Assigns the description field to a variable and adds validation
        description = (EditText) findViewById(R.id.add_portfolio_description_input);

        description.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30)
        });

        // Programmatically enable the EditText to sentence first letter capitalization
        description.setInputType(
                InputType.TYPE_CLASS_TEXT|
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );


        //Assigns the amount field to a variable
        amount = (EditText) findViewById(R.id.add_portfolio_amount_input);

        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_portfolio_activity_btn);

        //-----------------------------Event Listener Methods-----------------------------

        /**
         * nextButton.setOnClickListener()
         * Triggers if next button is clicked
         * Validates the data entered in the fields and if it's valid directs the user to the next activity (AddCompanyActivity)
         * @see com.example.portfoliobalancer.add_company_activity.AddCompanyActivity
         */
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try
                {
                    //Convert name and description to strings
                    String nameString = name.getText().toString().trim();
                    String descriptionString = description.getText().toString().trim();
                    //Convert amount to string
                    String amountString = amount.getText().toString().trim();

                    //Pass strings to validation method
                    validation.checkPortfolioDetailsValid(nameString, descriptionString, amountString);

                    //If everything is valid, create a portfolio and start a new activity (passing the portfolio to that activity)
                    Portfolio portfolio = createPortfolio(nameString, descriptionString, amountString);
                    Intent intent = new Intent(AddPortfolioActivity.this, AddCompanyActivity.class);
                    intent.putExtra("FROM_ACTIVITY", "add_portfolio");
                    intent.putExtra("portfolio", portfolio);
                    startActivity(intent);
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

    /**
     * createPortfolio()
     * Creates a basic portfolio with initial details
     * @param nameString
     * @param descriptionString
     * @param amountString
     * @return A new portfolio object
     *
     */
    private Portfolio createPortfolio(String nameString, String descriptionString, String amountString)
    {
        Portfolio p = new Portfolio();
        p.setId(portfolioId);
        p.setName(nameString);
        p.setDescription(descriptionString);
        p.setInitialPrice(Double.parseDouble(amountString));
        p.setPercentageChangeLimit(PERCENTAGE_CHANGE_LIMIT);

        return p;
    }
}
