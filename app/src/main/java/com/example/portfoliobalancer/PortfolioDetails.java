package com.example.portfoliobalancer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PortfolioDetails extends AppCompatActivity {

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
