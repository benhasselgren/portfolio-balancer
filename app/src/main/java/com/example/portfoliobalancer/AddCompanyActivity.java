package com.example.portfoliobalancer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.portfoliobalancer.classes.Company;
import com.example.portfoliobalancer.classes.Portfolio;

import java.util.ArrayList;

public class AddCompanyActivity extends AppCompatActivity {

    //Variables
    private Portfolio portfolio;
    private ArrayList<Company> availableCompanies;
    private ArrayList<Company> selectedCompanies;
    private ArrayList<String> companyTitles;
    private ArrayList<String> selectedCompanyTitles;
    //Views
    private AutoCompleteTextView companySelector;
    private ListView selectedCompaniesDisplay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_company);

        //Assign the intent parcelable extra to a variable
        portfolio = (Portfolio) getIntent().getParcelableExtra("portfolio");

        //Add views
        companySelector = (AutoCompleteTextView)findViewById(R.id.company_selector);
        selectedCompaniesDisplay = (ListView) findViewById(R.id.selected_companies);

        //Intitalise the selectedCompanies arraylist
        selectedCompanies = new ArrayList<>();
        selectedCompanyTitles = new ArrayList<>();

        //Adds the available companies from resources and adds them to array list
        getAvailableCompanies();

        ArrayAdapter<String> adapterSelector = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companyTitles);
        companySelector.setAdapter(adapterSelector);

        final ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedCompanyTitles);
        selectedCompaniesDisplay.setAdapter(adapterList);



        //Adds the item clicked to the selectedCompaniesArrayList and to the selceted companies list in the xaml file
        companySelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Get company code and find company using code
                String c_code = adapterView.getItemAtPosition(i).toString();
                Company c = findCompany(c_code);

                //If company already has been selected then show error message
                if(selectedCompanies.contains(c))
                {
                    Toast.makeText(getBaseContext(), "You have already added this company.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //add to selected companies arraylist
                    selectedCompanies.add(c);
                    //Update selected companies list display
                    selectedCompanyTitles.add(companyTitles.get(i));
                    adapterList.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "You have succesfully added " + c.getName() + ".", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void getAvailableCompanies()
    {
        //Initialise variable
        availableCompanies = new ArrayList<>();
        companyTitles = new ArrayList<>();

        //Get the companies from the string array and add them to the companies strings array list
        String[] strings = getResources().getStringArray(R.array.companies);

        for(String s : strings)
        {

            String[] result = s.split(",");
            String c_code = result[0];
            String c_name = result[1];
            Double c_price = Double.parseDouble(result[2]);

            Company c = new Company(c_name, c_code, 0, c_price, 0, 0, null );

            //Add company object to companies list and add company title to companyTitles list
            companyTitles.add(String.format("%s", c_code));
            availableCompanies.add(c);
        }
    }

    private Company findCompany(String c_code)
    {
        for(Company c: availableCompanies)
        {
            if(c.getCompanyCode().equals(c_code))
            {
                return c;
            }
        }
        return null;
    }
}
