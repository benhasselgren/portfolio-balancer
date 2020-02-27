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
    private static String portfoliosTag = "portfolios";

    //-----------------------------Getters/Setters-----------------------------


    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
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

    public void loadUserData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(portfoliosTag, null);
        Type type = new TypeToken<ArrayList<Portfolio>>() {}.getType();
        this.portfolios = gson.fromJson(json, type);

        if (this.portfolios == null) {
            this.portfolios = new ArrayList<>();
        }
    }

    public void saveUserData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this.portfolios);
        editor.putString(portfoliosTag, json);
        editor.apply();
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

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.portfolios);
    }

    protected UserData(Parcel in) {
        this.portfolios = in.createTypedArrayList(Portfolio.CREATOR);
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
