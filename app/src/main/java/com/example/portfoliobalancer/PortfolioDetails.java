package com.example.portfoliobalancer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.portfoliobalancer.classes.Portfolio;

//######################-----------------------------MainActivityClass-----------------------------######################
//XML file: activity_portfolio_details.xml
//Displays the portfolio details (List of companies, prices, growth, etc.)

public class PortfolioDetails extends AppCompatActivity {

    //-----------------------------On Create Method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_details);


        Portfolio portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        if (portfolio != null)
        {
            TextView portfolioName = (TextView) findViewById(R.id.portfolio_info_title);
            TextView portfolioDescription = (TextView) findViewById(R.id.portfolio_info_description);

            portfolioName.setText(portfolio.getName());
            portfolioDescription.setText(portfolio.getDescription());
        }
    }
}
