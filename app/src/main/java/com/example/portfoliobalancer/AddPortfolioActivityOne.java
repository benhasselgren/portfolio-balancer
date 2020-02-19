package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import com.example.portfoliobalancer.InputFilterMinMax;

public class AddPortfolioActivityOne extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_portfolio_activity_one);

        //Assigns the name field to a variable and adds validation
        EditText name = (EditText) findViewById(R.id.add_portfolio_name_input);

        name.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(15)
        });

        //Assigns the description field to a variable and adds validation
        final EditText description = (EditText) findViewById(R.id.add_portfolio_description_input);

        description.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30)
        });

        //Assigns the amount field to a variable and adds validation
        final EditText amount = (EditText) findViewById(R.id.add_portfolio_amount_input);

        amount.setFilters(new InputFilter[] {
                new InputFilterMinMax("100", "25000")
        });

        //Assigns the button to a variable
        Button nextButton = (Button) findViewById(R.id.add_portfolio_activity_one_btn);
    }
}
