package com.example.portfoliobalancer.business_logic_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.example.portfoliobalancer.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//######################-----------------------------UserDataClass-----------------------------######################
//Parcelable class that hold details about users portfolios
public class UserData implements Parcelable
{
    //-----------------------------Instance variables-----------------------------

    private List<Portfolio> portfolios = new ArrayList<Portfolio>();
    private List<Company> companies = new ArrayList<>();
    private static String portfoliosTag = "portfolios";
    private static String companiesTag = "companies";

    //-----------------------------Getters/Setters-----------------------------


    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    //-----------------------------Constructors-----------------------------

    public UserData()
    {
    }

    public UserData(List<Portfolio> portfolios)
    {
        this.portfolios = portfolios;
    }

    public UserData(UserData u)
    {
        this.portfolios = u.portfolios;
    }

    //-----------------------------Methods-----------------------------

    public void addPortfolio(Portfolio p)
    {
        this.portfolios.add(p);
    }

    public void removePortfolio(Portfolio p)
    {
        this.portfolios.remove(p);
    }

    public void updatePortfolio(Portfolio add_p, Portfolio remove_p)
    {
        this.removePortfolio(remove_p);
        this.addPortfolio(add_p);
    }

    public Portfolio findPortfolioById(int id)
    {
        for(Portfolio p : this.portfolios)
        {
            if(p.getId() == id)
            {
                return p;
            }
        }
        return null;
    }

    public void loadUserData(Context context, boolean load_save_portfolio)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        if(load_save_portfolio)
        {
            String json = sharedPreferences.getString(portfoliosTag, null);
            Type type = new TypeToken<ArrayList<Portfolio>>() {}.getType();
            this.portfolios = gson.fromJson(json, type);

            if (this.portfolios == null) {
                this.portfolios = new ArrayList<>();
            }
        }
        else
        {
            String json = sharedPreferences.getString(companiesTag, null);
            Type type = new TypeToken<ArrayList<Company>>() {}.getType();
            this.companies = gson.fromJson(json, type);

            if (this.companies == null) {
                this.companies = new ArrayList<>();
            }
        }
    }

    public void saveUserData(Context context, boolean load_save_portfolio)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        if(load_save_portfolio)
        {
            String json = gson.toJson(this.portfolios);
            editor.putString(portfoliosTag, json);
            editor.apply();
        }
        else
        {
            String json = gson.toJson(this.companies);
            editor.putString(companiesTag, json);
            editor.apply();
        }
    }

    public void checkPortfoliosAreBalanced(String[] strings)
    {
        ArrayList<Company> updatedCompanies = new ArrayList<>();
        //Get the companies from the string array and add them to the companies strings array list
        for(String s : strings)
        {
            String[] result = s.split(",");
            String c_code = result[0];
            String c_name = result[1];
            Double c_price = Double.parseDouble(result[2]);

            //Create a new company with the basic details
            Company c = new Company();
            c.setName(c_name);
            c.setCompanyCode(c_code);
            c.setCostPrice(c_price);

            //Add company object to updated companies list
            updatedCompanies.add(c);
        }

        for(Portfolio p : portfolios)
        {
            p.checkPortfolioIsBalanced(updatedCompanies);
        }
    }

    public void createCompanies()
    {
        Company c1 = new Company("Apple Inc", "AAPL", 273.11);
        Company c2 = new Company("Admiral Group", "ADM", 21.31);
        Company c3 = new Company("Coca Cola Consolidated Inc", "COKE", 201.46);
        Company c4 = new Company("Vanguard Active U.K. Equity Fund A GBP Accumulation", "VAAUE", 98.9614);
        Company c5 = new Company("Vanguard Emerging Markets Stock Index", "VNEMI", 180.6317);
        Company c6 = new Company("Baillie Gifford Global Alpha Growth Fund B Accumulation", "BFGGA", 3.346);
        Company c7 = new Company("Baillie Gifford Global Income Growth Fund B Accumulation", "BFGIA", 15.70);
        Company c8 = new Company("Tesla Inc", "TSLA", 678.89);

        this.companies.add(c1);
        this.companies.add(c2);
        this.companies.add(c3);
        this.companies.add(c4);
        this.companies.add(c5);
        this.companies.add(c6);
        this.companies.add(c7);
        this.companies.add(c8);
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.portfolios);
        dest.writeTypedList(this.companies);
    }

    protected UserData(Parcel in) {
        this.portfolios = in.createTypedArrayList(Portfolio.CREATOR);
        this.companies = in.createTypedArrayList(Company.CREATOR);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
