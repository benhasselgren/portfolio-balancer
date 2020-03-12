package com.example.portfoliobalancer.companies_prices_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.add_company_activity.AddCompanyActivity;
import com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity;
import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.business_logic_classes.Validation;
import com.example.portfoliobalancer.main_activity.MainActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

/**
 * CompaniesPricesActivity
 * Companies page of app. It shows the user all the companies they have added to the application.
 * The user can add/edit/remove companies
 * XML file: activity_main.xml
 */

public class CompaniesPricesActivity extends AppCompatActivity {

    //-----------------------------Variables/Views-----------------------------
    //Variables
    private UserData userData;
    private Context context;
    private Validation validation = new Validation();
    //Views
    private EditText name;
    private EditText code;
    private EditText price;
    private Button add_company_btn;
    private RecyclerView companiesListView;
    private ActionBar toolbar;

    //-----------------------------On Create method-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_prices);

        //Prevents keyboard from popping up intially
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Create navigation bar
        //Reference https://www.youtube.com/watch?v=2LtObBTF9CM
        toolbar = getSupportActionBar();
        toolbar.setTitle("Companies");

        final BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.companies_menu);

        //Set context
        context = getApplicationContext();

        //Create a new UserData object
        userData = new UserData();

        // Load the COMPANIES (hence parameter passed is FALSE)
        userData.loadUserData(context, false);

        if(userData.getCompanies() != null)
        {
            //Add views
            name = (EditText) findViewById(R.id.companies_name_input);
            code = (EditText) findViewById(R.id.companies_code_input);
            price = (EditText) findViewById(R.id.companies_price_input);

            add_company_btn = (Button)findViewById(R.id.companies_add_btn);

            companiesListView = (RecyclerView)findViewById(R.id.companies_prices_list);

            //If the recyclerview doesn't change size, we can set this true and
            companiesListView.setHasFixedSize(true);

            //Initialize the Portfolios adapter, which binds the data to the entry view
            CompaniesPricesAdapter adapter = new CompaniesPricesAdapter(this, R.layout.company_price_entry, userData.getCompanies());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            companiesListView.setLayoutManager(layoutManager);

            companiesListView.setAdapter(adapter);

            ItemTouchHelper itemTouchHelper = new
                    ItemTouchHelper(new SwipeToDeleteCallback(adapter, getApplicationContext()));
            itemTouchHelper.attachToRecyclerView(companiesListView);

            //-----------------------------Event Listener Methods-----------------------------

            /**
             * add_company_btn.setOnClickListener()
             * Triggers if add_company_btn clicked
             * The company will be added the companies list
             * @see com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity
             */
            add_company_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try
                    {
                        //Convert code and name to strings
                        String codeString = code.getText().toString().trim().toUpperCase();
                        String nameString = name.getText().toString().trim();
                        //Convert price to string
                        String priceString = price.getText().toString().trim();

                        //Pass strings to validation method
                        validation.checkCompanyDetailsValid(codeString, nameString, priceString);

                        //If everything is valid, create a company and and add it to the list
                        Company c = new Company(nameString, codeString, Double.parseDouble(priceString));

                        //add to companies arraylist
                        userData.getCompanies().add(c);
                        companiesListView.getAdapter().notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "You have succesfully added " + c.getName() + ".", Toast.LENGTH_SHORT).show();

                        //Close keyboard
                        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    catch (RuntimeException ex)
                    {
                        //Catches all exceptions here and displays appropriate error message
                        Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /**
             * KeyboardVisibilityEvent
             * Hides the bottom navigation if keyboard clicked
             * Prevents the bottom nav from getting pushed up by keyboard
             * Reference https://stackoverflow.com/questions/52391743/bottom-navigation-bar-moves-up-with-keyboard
             */
            KeyboardVisibilityEvent.setEventListener(
                    this,
                    new KeyboardVisibilityEventListener() {
                        @Override
                        public void onVisibilityChanged(boolean isOpen) {
                            if(isOpen){
                                navigation.setVisibility(View.INVISIBLE);
                            }else{
                                navigation.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    /**
     * mOnNavigationItemSelectedListener
     * Handles bottom navigation bar clicks.
     * Reference https://www.youtube.com/watch?v=2LtObBTF9CM
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_menu:
                    //Save companies
                    userData.saveUserData(getApplicationContext(), false);
                    Toast.makeText(getBaseContext(), "Updated companies", Toast.LENGTH_SHORT).show();

                    /**
                     * The user will be directed to the MainActivity
                     * @see com.example.portfoliobalancer.main_activity.MainActivity
                     */
                    Intent intent = new Intent(CompaniesPricesActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NO_ANIMATION );
                    startActivity(intent);
                    return true;
                case R.id.companies_menu:
                    return true;
            }
            return false;
        }
    };
}
