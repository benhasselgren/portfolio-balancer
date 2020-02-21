package com.example.portfoliobalancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.portfoliobalancer.classes.Portfolio;

public class AddPortfolioActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText amount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_portfolio_activity_one);

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
        Button nextButton = (Button) findViewById(R.id.add_portfolio_activity_one_btn);

        //Triggers if button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try
                {
                    //Convert name and description to strings
                    String nameString = name.getText().toString().trim();
                    String descriptionString = description.getText().toString().trim();
                    //Convert amount to string, then int
                    String amountString = amount.getText().toString().trim();

                    //Pass strings to validation method
                    checkValidation(nameString, descriptionString, amountString);

                    //If everything is valid, create a portfolio and start a new activity (passing the portfolio to that activity)
                    Portfolio portfolio = new Portfolio(nameString, descriptionString, null, Double.parseDouble(amountString), Double.parseDouble(amountString), null, true, null, 5 );
                    Toast.makeText(getBaseContext(), "Valid", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPortfolioActivity.this, AddCompanyActivity.class);
                    intent.putExtra("portfolio", portfolio);
                    startActivity(intent);
                }
                catch (RuntimeException ex)
                {
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkValidation(String nameString, String descriptionString, String amountString)
    {
        if (nameString.isEmpty())
        {
            throw new RuntimeException("Name field cannot be empty.");
        }
        else if(descriptionString.isEmpty())
        {
            throw new RuntimeException("Description field cannot be empty.");
        }
        else if(amountString.isEmpty())
        {
            throw new RuntimeException("Amount field cannot be empty.");
        }
        else {
            int amountInt = Integer.parseInt(amountString);
            if (amountInt < 100 || amountInt > 25000)
            {
                throw new RuntimeException("Amount has to be between £100-£25,000.");
            }
        }
    }
}
