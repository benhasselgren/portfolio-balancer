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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_portfolio_activity_one);

        //Assigns the name field to a variable and adds validation
        final EditText name = (EditText) findViewById(R.id.add_portfolio_name_input);

        name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(15)
        });

        //Assigns the description field to a variable and adds validation
        final EditText description = (EditText) findViewById(R.id.add_portfolio_description_input);

        description.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30)
        });

        //Assigns the amount field to a variable
        final EditText amount = (EditText) findViewById(R.id.add_portfolio_amount_input);

        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_portfolio_activity_one_btn);

        //Triggers if button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Convert amount to string, then int
                String amountString = name.getText().toString();
                int amountInt = Integer.parseInt(amountString);
                //Convert namd and description to strings
                String nameString = name.getText().toString();
                String descriptionString = name.getText().toString();

                //Set both validators to false intially
                boolean amountValid = false;
                boolean textValid = false;

                //If amount is within range then set amountValid to true
                if (amountInt >= 100 && amountInt <= 25000)
                {
                    amountValid = true;

                    //If name and descrption both have values then set textValid to true
                    if(nameString.length() > 0 && descriptionString.length() > 0)
                    {
                        textValid = true;
                    }
                }

                //Display appropriate messages if everything is valid or not valid
                if(amountValid)
                {
                    if(textValid)
                    {
                        Toast.makeText(getBaseContext(), "Hurray!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "You need to fill in all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Amount needs to be between £100 and £25,000", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
