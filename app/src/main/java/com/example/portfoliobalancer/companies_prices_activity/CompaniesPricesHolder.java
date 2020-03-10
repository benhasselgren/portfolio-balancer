package com.example.portfoliobalancer.companies_prices_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;

/**
 * CompaniesPricesHolder
 * This displays the companies that are to be added to a portfolio
 * XML file: company_price_entry.xml
 */

public class CompaniesPricesHolder extends RecyclerView.ViewHolder{

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private Company company;
    private Context context;
    //Views
    private final TextView name;
    private final TextView code;
    private final EditText price;

    //-----------------------------Constructor-----------------------------
    public CompaniesPricesHolder(Context context, View itemView) {
        super(itemView);

        //Set context
        this.context = context;

        //Add views
        name = (TextView) itemView.findViewById(R.id.entry_company_price_name);
        code = (TextView) itemView.findViewById(R.id.entry_company_price_code);
        price = (EditText) itemView.findViewById(R.id.entry_company_price_price);

        /**
         * company_target_percentage_value.addTextChangedListener()
         * Updates the seekbar progress value if text is changed
         */
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Only update if string is not empty
                if(price.getText().length() > 0 ) {
                    company.setCostPrice(Double.parseDouble(price.getText().toString().trim()));
                }
            }
        });
    }

    public void bindCompanyPrice(Company company) {
        // Bind the data to all the ViewHolders
        this.company = company;

        // Name Field
        name.setText(company.getName());

        // code Field
        code.setText(company.getCompanyCode());

        // Current company price Field
        price.setText(String.format("%.2f", company.getCostPrice()));
    }
}
