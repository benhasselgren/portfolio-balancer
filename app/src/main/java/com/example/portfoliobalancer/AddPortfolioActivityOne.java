package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.portfoliobalancer.InputFilterMinMax;

public class AddPortfolioActivityOne extends AppCompatActivity {

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
                    checkValidation();
                    Toast.makeText(getBaseContext(), "Everything is valid", Toast.LENGTH_SHORT).show();
                }
                catch (IllegalArgumentException ex)
                {
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkValidation()
    {
        //Convert amount to string, then int
        String amountString = name.getText().toString();
        int amountInt = Integer.parseInt(amountString);
        //Convert name and description to strings
        String nameString = name.getText().toString();
        String descriptionString = name.getText().toString();

        if (nameString.length() <= 0)
        {
            throw new IllegalArgumentException("Name field cannot be empty");
        }
        else if(descriptionString.length() <=0)
        {
            throw new IllegalArgumentException("Description field cannot be empty");
        }
        else if(amountString.length() <=0)
        {
            throw new IllegalArgumentException("Amount field cannot be empty");
        }
        else {
            if (amountInt < 100 || amountInt > 25000)
            {
                throw new IllegalArgumentException("Amount has to be between £100-£25,000");
            }
        }
    }
}
