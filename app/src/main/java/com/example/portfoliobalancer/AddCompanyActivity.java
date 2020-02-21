package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.portfoliobalancer.classes.Portfolio;

public class AddCompanyActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);

        //Assignt the intent parcelable extra to a variable
        Portfolio portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");
    }

}
