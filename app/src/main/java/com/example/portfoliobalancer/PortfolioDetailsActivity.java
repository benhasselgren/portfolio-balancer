package com.example.portfoliobalancer;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

//######################-----------------------------MainActivityClass-----------------------------######################
//XML file: activity_portfolio_details.xml
//Displays the portfolio details (List of companies, prices, growth, etc.)

public class PortfolioDetailsActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    //Views
    private TextView currentPrice;
    private TextView growth;
    private TextView lastRebalanced;
    private ImageView settingsBtn;
    private Button rebalanceBtn;

    //-----------------------------On Create Method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_details);

        //Assign the intent parcelable extra to a variable
        Portfolio portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        if (portfolio != null)
        {
            //Add views
            currentPrice = (TextView) findViewById(R.id.portfolio_current_price);
            growth = (TextView) findViewById(R.id.portfolio_growth);
            lastRebalanced = (TextView) findViewById(R.id.portfolio_last_rebalanced);
            settingsBtn = (ImageView) findViewById(R.id.portfolio_settings);
            rebalanceBtn = (Button) findViewById(R.id.portfolio_rebalance_btn);

            //Bind data to views
            currentPrice.setText(String.format("£%.2f", portfolio.getCurrentPrice()));
            currentPrice.setText(String.for);
        }
    }
}
