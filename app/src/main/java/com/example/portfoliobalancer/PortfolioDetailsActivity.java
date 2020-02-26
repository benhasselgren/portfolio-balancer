package com.example.portfoliobalancer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//######################-----------------------------PortfolioDetailsActivity-----------------------------######################
//XML file: activity_portfolio_details.xml
//Displays the portfolio details (List of companies, prices, growth, etc.)

public class PortfolioDetailsActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Context context;
    //Views
    private TextView currentPrice;
    private TextView growth;
    private TextView lastRebalanced;
    private ImageButton settingsBtn;
    private Button rebalanceBtn;
    private RecyclerView companiesListView;

    //-----------------------------On Create Method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_details);

        //Set the context
        context = getApplicationContext();

        //Assign the intent parcelable extra to a variable
        final Portfolio portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        if (portfolio != null)
        {
            //Add views
            currentPrice = (TextView) findViewById(R.id.portfolio_current_price);
            growth = (TextView) findViewById(R.id.portfolio_growth);
            lastRebalanced = (TextView) findViewById(R.id.portfolio_last_rebalanced);
            settingsBtn = (ImageButton) findViewById(R.id.portfolio_settings);
            rebalanceBtn = (Button) findViewById(R.id.portfolio_rebalance_btn);

            companiesListView = (RecyclerView)findViewById(R.id.companies_list);

            //If the recyclerview doesn't change size, we can set this true and
            companiesListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            CompaniesAdapter adapter = new CompaniesAdapter(this, R.layout.company_entry, portfolio.getCompanies());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            companiesListView.setLayoutManager(layoutManager);

            companiesListView.setAdapter(adapter);

            //Bind data

            //Set current price
            currentPrice.setText(String.format("£%.2f", portfolio.getCurrentPrice(false)));

            // Set growth
            if(portfolio.getPriceGrowth() > 0)
            {
                growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
                growth.setText(String.format("+£%.2f(%.2f%%)", portfolio.getPriceGrowth(), portfolio.getPercentageGrowth()));
            }
            else if (portfolio.getPriceGrowth() < 0)
            {
                growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetDecline));
                growth.setText(String.format("+£%.2f(%.2f%%)", portfolio.getPriceGrowth(), portfolio.getPercentageGrowth()));
            }
            else {
                growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAsset));
                growth.setText("£00.00(0.0%)");
            }

            //Set last rebalanced
            DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
            String date = formatter.format(portfolio.getLastRebalanced());
            lastRebalanced.setText(String.format("Last rebalanced: %s", date));

            //-----------------------------Event Listener Methods-----------------------------
            //Add portfolio button clicked event handled here
            settingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PortfolioDetailsActivity.this, PortfolioSettingsActivity.class);
                    intent.putExtra("portfolio", portfolio);
                    intent.putExtra("FROM_ACTIVITY", "portfolio_details");
                    startActivity(intent);
                }
            });
        }
    }
}
