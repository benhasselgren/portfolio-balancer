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

//######################-----------------------------UserDataClass-----------------------------######################
//Parcelable class that hold details about users portfolios
public class UserData implements Parcelable
{
    //-----------------------------Instance variables-----------------------------

    private List<Portfolio> portfolios = new ArrayList<Portfolio>();

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

    public List<Portfolio> loadUserData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Portfolio>>() {}.getType();
        portfolios = gson.fromJson(json, type);

        if (portfolios == null) {
            portfolios = new ArrayList<>();
        }

        return portfolios;
    }

    public void saveUserData(List<Portfolio> p, Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(portfolios);
        editor.putString("task list", json);
        editor.apply();
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
