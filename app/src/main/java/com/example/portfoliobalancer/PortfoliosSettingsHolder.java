package com.example.portfoliobalancer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.portfoliobalancer.business_logic_classes.Company;

//######################-----------------------------PortfolioSettingsHolderClass-----------------------------######################
//XML file: settings_target_percentage_entry.xml
//This displays the sliders to adjust the companies target percentages.

public class PortfoliosSettingsHolder extends RecyclerView.ViewHolder {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views
    private final TextView company_name;
    private final SeekBar company_target_percentage_seekbar;
    private final TextView company_target_percentage_value;

    //-----------------------------Constructor-----------------------------
    public PortfoliosSettingsHolder(Context context, View itemView)
    {
        super(itemView);

        //Set context
        this.context = context;

        //Add views
        this.company_name = (TextView) itemView.findViewById(R.id.entry_company_name);
        this.company_target_percentage_seekbar = (SeekBar) itemView.findViewById(R.id.entry_company_target_percentage_seekBar);
        this.company_target_percentage_value = (TextView) itemView.findViewById(R.id.entry_company_target_percentage);

        //-----------------------------Event Listener Methods----------------------------
        //Updates the value of the seekbar and displays it in the value text view
        company_target_percentage_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress_tracker;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //Update the text value with the seekbar value as it changes
                progress_tracker = progress;
                company_target_percentage_value.setText(String.format("%s%%",progress_tracker));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Once user has removed finger then update company target percentage
                company.setTargetPercentage(Integer.valueOf(progress_tracker));
            }
        });
    }

    //-----------------------------Methods-----------------------------

    //Bind the data of the company to the views in the activity
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

    //Sets up the progress bar to show the companies current target percentage
    public void setUpSeekBar()
    {
        company_target_percentage_seekbar.setProgress(company.getTargetPercentage());
        company_target_percentage_seekbar.setMax(100);
    }
}
