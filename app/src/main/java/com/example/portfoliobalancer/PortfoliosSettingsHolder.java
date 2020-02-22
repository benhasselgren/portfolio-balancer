package com.example.portfoliobalancer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.portfoliobalancer.classes.Company;
import com.example.portfoliobalancer.classes.Portfolio;

public class PortfoliosSettingsHolder  extends RecyclerView.ViewHolder {
    private Company company;
    private Context context;

    private final TextView company_name;
    private final SeekBar company_target_percentage_seekbar;
    private final TextView company_target_percentage_value;

    public PortfoliosSettingsHolder(Context context, View itemView)
    {
        super(itemView);

        //Set context
        this.context = context;

        //Add views
        this.company_name = (TextView) itemView.findViewById(R.id.entry_company_name);
        this.company_target_percentage_seekbar = (SeekBar) itemView.findViewById(R.id.entry_company_target_percentage_seekBar);
        this.company_target_percentage_value = (TextView) itemView.findViewById(R.id.entry_company_target_percentage);
    }

    public void bindPortfolio(Company company) {
        // Bind the data to all the ViewHolders
        this.company = company;

        // ------------------- Name Field -------------------
        this.company_name.setText(company.getCompanyCode());

        // ------------------- Seekbar -------------------
        setUpSeekBar();

        // ------------------- Target Percentage -------------------
        this.company_target_percentage_value.setText(String.format("%s%%",company.getTargetPercentage()));
    }
    public void setUpSeekBar()
    {
        company_target_percentage_seekbar.setProgress(company.getTargetPercentage());
        company_target_percentage_seekbar.setMax(100);
    }
}
