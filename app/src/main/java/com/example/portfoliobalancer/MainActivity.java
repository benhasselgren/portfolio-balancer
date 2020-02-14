package com.example.portfoliobalancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView portfoliosListView;
    private ArrayList<Portfolio> portfolios;
    private ArrayList<Company> companies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        portfoliosListView = (RecyclerView)findViewById(R.id.portfolios_list);
        // if the recyclerview doesn't change size, we can set this true and
        portfoliosListView.setHasFixedSize(true);

        // get the data
        loadPlaces();
    }

    private void loadPlaces() {

        portfolios = new ArrayList<Portfolio>();
        companies = new ArrayList<Company>();
        Date date = new Date();

        Portfolio portfolio = new Portfolio("Test Portfolio", "A test portfolio", companies, 20000, 20000, date, false, date, 5 );

        Company Apple = new Company("Apple Inc", "AAPL", 0, 324, 70, 0, date);

        Company Microsoft = new Company(
                R.string.location_parliament_hill_name,
                R.drawable.parliament_hill,
                R.string.location_parliament_hill_info);

        Company Tesla = new Company(
                R.string.location_niagara_falls_name,
                R.drawable.niagara_falls,
                R.string.location_niagara_falls_info);

        for(int i=0;i<6;i++)

        {
            places.add(hopewell_rocks);
            places.add(parliament);
            places.add(niagara);
        }
    }
}
