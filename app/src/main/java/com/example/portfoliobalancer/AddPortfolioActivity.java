package com.example.portfoliobalancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.business_logic_classes.Validation;

//######################-----------------------------AppPortfolioActivityClass-----------------------------######################
//XML file: activity_add_portfolio.xml
//User adds the portfolio name, description and amount to invest here

public class AddPortfolioActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Validation validation = new Validation();
    private UserData userData;
    //Views
    private EditText name;
    private EditText description;
    private EditText amount;

    //-----------------------------On Create method-----------------------------
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portfolio);

        //Assigns the name field to a variable and adds validation
        name = (EditText) findViewById(R.id.add_portfolio_name_input);

        name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(15)
        });

        //Assigns the description field to a variable and adds validation
        description = (EditText) findViewById(R.id.add_portfolio_description_input);

        description.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30)
        });

        //Assigns the amount field to a variable
        amount = (EditText) findViewById(R.id.add_portfolio_amount_input);

        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_portfolio_activity_btn);

        //-----------------------------Event Listener Methods-----------------------------
        //Triggers if next button is clicked
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
                    Portfolio portfolio = new Portfolio(nameString, descriptionString, null, Double.parseDouble(amountString), Double.parseDouble(amountString), null, true, null, 5 );
                    Intent intent = new Intent(AddPortfolioActivity.this, AddCompanyActivity.class);
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
}
