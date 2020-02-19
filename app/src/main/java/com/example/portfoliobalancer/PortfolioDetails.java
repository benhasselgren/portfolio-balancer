package com.example.portfoliobalancer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.portfoliobalancer.classes.Portfolio;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
