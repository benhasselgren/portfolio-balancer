package com.example.portfoliobalancer.portfolio_details_activity;

import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.example.portfoliobalancer.portfolio_settings_activity.PortfolioSettingsActivity;
import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.main_activity.MainActivity;

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
    private ProgressDialog progressDialog;

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
                growth.setText(String.format("+£%.2f(+%.2f%%)", portfolio.getPriceGrowth(), portfolio.getPercentageGrowth()));
            }
            else if (portfolio.getPriceGrowth() < 0)
            {
                growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAssetDecline));
                growth.setText(String.format("-£%.2f(-%.2f%%)", Math.abs(portfolio.getPriceGrowth()), Math.abs(portfolio.getPercentageGrowth())));
            }
            else {
                growth.setTextColor(ContextCompat.getColor(context, R.color.textColorAsset));
                growth.setText("£00.00(0.0%)");
            }

            //Set last rebalanced
            DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
            String date = formatter.format(portfolio.getLastRebalanced());
            lastRebalanced.setText(String.format("Last rebalanced: %s", date));

            //Set the rebalance button to active if it's unbalanced
            if(!portfolio.isBalanced())
            {
                rebalanceBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.textColorAssetGrowth));
                rebalanceBtn.setEnabled(true);
            }
            else
            {
                //Disable button if it is balanced
                rebalanceBtn.setEnabled(false);
            }

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

            //Add portfolio button clicked event handled here
            rebalanceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Initialize the progress dialog
                    progressDialog = new ProgressDialog(PortfolioDetailsActivity.this);
                    progressDialog.setIndeterminate(true);
                    // Progress dialog horizontal style
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    // Progress dialog title
                    progressDialog.setTitle( getResources().getString(R.string.balance_dialog_title));
                    // Progress dialog message
                    progressDialog.setMessage(getResources().getString(R.string.balance_dialog_message));

                    //Start the process dialog
                    progressDialog.show();

                    //Balance portfolio
                    portfolio.balancePortfolio(false);


                    //Load portfolios, check if this portfolio exists and add or update portfolio, then save portfolios
                    //Load
                    UserData ud = new UserData();
                    ud.loadUserData(getApplicationContext());

                    //Check if it exists
                    Portfolio p = ud.findPortfolioById(portfolio.getId());

                    if (p != null)
                    {
                        //If it does then update
                        ud.updatePortfolio(portfolio, p);
                    }
                    else
                    {
                        //If it doesn't then add it to portfolios
                        ud.addPortfolio(portfolio);
                    }

                    //Save portfolios
                    ud.saveUserData(getApplicationContext());

                    //End process dialog
                    progressDialog.dismiss();

                    //Go back to main page
                    Intent intent = new Intent(PortfolioDetailsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }
}
