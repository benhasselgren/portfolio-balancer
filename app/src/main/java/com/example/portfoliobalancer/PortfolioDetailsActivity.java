package com.example.portfoliobalancer;

import android.media.Image;
import android.support.v4.content.ContextCompat;
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

        // Overall growth field Field
        if(portfolio.getGrowth() > 0)
        {
            growth.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorAssetGrowth));
            growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else if (portfolio.getGrowth() < 0)
        {
            growth.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorAssetDecline));
            growth.setText(String.format("+£%.2f(0.0%%)", portfolio.getCurrentPrice()));
        }
        else {
            growth.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorAsset));
            growth.setText("£00.00(0.0%)");
        }
    }
}
