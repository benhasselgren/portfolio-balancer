package com.example.portfoliobalancer.business_logic_classes;

import android.os.Parcel;
import android.os.Parcelable;

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

    public List<Portfolio> loadUserData()
    {
        return null;
    }

    public void saveUserData(List<Portfolio> p)
    {

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
