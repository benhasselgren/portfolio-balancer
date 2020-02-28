package com.example.portfoliobalancer.add_company_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;

//######################-----------------------------AddCompanyHolderClass-----------------------------######################
//XML file: add_company_entry.xml
//This displays the companies that can be added to a portfolio
public class AddCompanyHolder extends RecyclerView.ViewHolder{

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views
    private final TextView name;
    private final TextView code;
    private final TextView price;

    //-----------------------------Constructor-----------------------------
    public AddCompanyHolder(Context context, View itemView) {
        super(itemView);

        //Set context
        this.context = context;

        //Add views
        name = (TextView) itemView.findViewById(R.id.entry_add_company_name);
        code = (TextView) itemView.findViewById(R.id.entry_add_company_code);
        price = (TextView) itemView.findViewById(R.id.entry_add_company_price);
    }

    public void bindAddCompany(Company company) {
        // Bind the data to all the ViewHolders
        this.company = company;

        // Name Field
        name.setText(company.getName());

        // Description Field
        code.setText(company.getCompanyCode());

        // Current portfolio price Field
        price.setText(String.format("£%.2f", company.getCostPrice()));
    }
}
