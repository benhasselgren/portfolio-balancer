package com.example.portfoliobalancer.business_logic_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * UserData
 * Parcelable class that hold details about users portfolios
 */

public class UserData implements Parcelable
{
    //-----------------------------Instance variables-----------------------------

    private List<Portfolio> portfolios = new ArrayList<Portfolio>();
    private List<Company> companies = new ArrayList<Company>();
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

    /**
     * UserData()
     * Creates an empty UserData object
     */
    public UserData()
    {
    }

    /**
     * UserData()
     * Creates a UserData object with a list of portfolios
     */
    public UserData(List<Portfolio> portfolios)
    {
        this.portfolios = portfolios;
    }

    /**
     * UserData()
     * Creates a UserData object from another UserData object
     */
    public UserData(UserData u)
    {
        this.portfolios = u.portfolios;
    }

    //-----------------------------Methods-----------------------------

    /**
     * findCompanyByCode()
     * Find a company in the companies list by the code .
     * @param code
     * @return The company found or null if it's not found
     */
    public Company findCompanyByCode(String code)
    {
        for(Company c : this.companies)
        {
            if(c.getCompanyCode() == code)
            {
                return c;
            }
        }
        return null;
    }

    /**
     * updatePortfolio()
     * Removes the old company from companies list then adds the updated company to companies list
     * @param add_c
     * @param remove_c
     */

    public void updateCompany(Company add_c, Company remove_c)
    {
        if(add_c != null && remove_c != null)
        {
            this.companies.remove(remove_c);
            this.companies.add(add_c);
        }
    }

    /**
     * addPortfolio()
     * Adds a portfolio to the portfolio list
     * @param p
     */
    public void addPortfolio(Portfolio p)
    {
        if(p!=null)
        {
            this.portfolios.add(p);
        }
    }

    /**
     * removePortfolio()
     * Removes a portfolio to the portfolio list
     * @param p
     */
    public void removePortfolio(Portfolio p)
    {
        this.portfolios.remove(p);
    }

    /**
     * updatePortfolio()
     * Removes the old portfolio from portfolio list then adds the updated portfolio to portfolio list
     * @param add_p
     * @param remove_p
     */
    public void updatePortfolio(Portfolio add_p, Portfolio remove_p)
    {
        if(add_p != null && remove_p != null)
        {
            this.removePortfolio(remove_p);
            this.addPortfolio(add_p);
        }
    }

    /**
     * findPortfolioById()
     * Find a portfolio int he portfolio list by the portfolio id.
     * @param id
     * @return The portolfio found or null if it's not found
     */
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

    /**
     * loadUserData()
     * Loads either users portfolios or list of companies from device depending on the boolean value entered.
     * If true then load portfolios.
     * if false then load companies.
     * @param context
     * @param load_save_portfolio
     */
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

    /**
     * saveUserData()
     * Saves either users portfolios or list of companies from device depending on the boolean value entered.
     * If true then save portfolios.
     * if false then save companies.
     * @param context
     * @param load_save_portfolio
     */
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

    /**
     * checkPortfoliosAreBalanced()
     * Checks to see if all portfolio are balanced
     * Does this by calling checkPortfolioIsBalanced() on each portfolio
     * @param updatedCompanies
     */
    public void checkPortfoliosAreBalanced(ArrayList<Company> updatedCompanies)
    {
        if (updatedCompanies != null)
        {
            for(Portfolio p : portfolios)
            {
                p.checkPortfolioIsBalanced(updatedCompanies);
            }
        }
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
