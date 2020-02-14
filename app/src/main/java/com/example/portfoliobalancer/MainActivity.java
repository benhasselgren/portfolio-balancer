package com.example.portfoliobalancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView portfoliosListView;
    private ArrayList<Portfolio> portfolios;
    private ArrayList<Company> companies;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portfoliosListView = (RecyclerView)findViewById(R.id.portfolios_list);
        // if the recyclerview doesn't change size, we can set this true and
        portfoliosListView.setHasFixedSize(true);

        // get the data
        loadPlaces();
        Log.i("Program:", "Working here");

        // Initialize the Places adapter, which binds the data to the entry view
        PortfoliosAdapter adapter = new PortfoliosAdapter(this, R.layout.portfolio_entry, userData.getPortfolios());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        portfoliosListView.setLayoutManager(layoutManager);

        portfoliosListView.setAdapter(adapter);
        // You can change animation of items and decorations
        // by using ItemAnimator and ItemDecorators and binding them
        // by calling to appropriate method attractionsListView.setItem...
    }

    private void loadPlaces() {

        portfolios = new ArrayList<Portfolio>();
        companies = new ArrayList<Company>();
        userData = new UserData(portfolios);
        Date date = new Date();

        Portfolio portfolio = new Portfolio("Test Portfolio", "A test portfolio", companies, 20000, 20000, date, false, date, 5 );

        Company Apple = new Company("Apple Inc", "AAPL", 0, 324.95, 70, 0, date );

        Company Microsoft = new Company("Microsoft Corporation", "MSFT", 0 , 183.72, 15, 0, date);

        Company Tesla = new Company("Tesla Inc", "TSLA", 0, 803.95, 15, 0, date);

        portfolio.addCompany(Apple);
        portfolio.addCompany(Microsoft);
        portfolio.addCompany(Tesla);

        for(int i=0;i<6;i++)
        {
            userData.addPortfolio(portfolio);
        }
    }
}
