package com.example.portfoliobalancer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Portfolio implements Parcelable
{
    //-----------------------------Instance variables-----------------------------
    private String name;
    private String description;
    private List<Company> companies = new ArrayList<Company>();
    private float currentPrice;
    private float initialPrice;
    private Date lastRebalanced;
    private boolean balanced;
    private Date currentUnitPriceDate;
    private int percentageChangeLimit;

    //-----------------------------Getters/Setters-----------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Date getLastRebalanced() {
        return lastRebalanced;
    }

    public void setLastRebalanced(Date lastRebalanced) {
        this.lastRebalanced = lastRebalanced;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    public Date getCurrentUnitPriceDate() {
        return currentUnitPriceDate;
    }

    public void setCurrentUnitPriceDate(Date currentUnitPriceDate) {
        this.currentUnitPriceDate = currentUnitPriceDate;
    }

    public int getPercentageChangeLimit() {
        return percentageChangeLimit;
    }

    public void setPercentageChangeLimit(int percentageChangeLimit) {
        this.percentageChangeLimit = percentageChangeLimit;
    }

    //-----------------------------Constructors-----------------------------

    public Portfolio(String name, String description, List<Company> companies, float currentPrice, float initialPrice, Date lastRebalanced, boolean balanced, Date currentUnitPriceDate, int percentageChangeLimit)
    {
        this.name = name;
        this.description = description;
        this.companies = companies;
        this.currentPrice = currentPrice;
        this.initialPrice = initialPrice;
        this.lastRebalanced = lastRebalanced;
        this.balanced = balanced;
        this.currentUnitPriceDate = currentUnitPriceDate;
        this.percentageChangeLimit = percentageChangeLimit;
    }

    public Portfolio(Portfolio p)
    {
        this.name = p.name;
        this.description = p.description;
        this.companies = p.companies;
        this.currentPrice = p.currentPrice;
        this.initialPrice = p.initialPrice;
        this.lastRebalanced = p.lastRebalanced;
        this.balanced = p.balanced;
        this.currentUnitPriceDate = p.currentUnitPriceDate;
        this.percentageChangeLimit = p.percentageChangeLimit;
    }


    protected Portfolio(Parcel in)
    {
        this.name = in.readString();
        this.description = in.readString();
        this.companies = in.readArrayList(null);
        this.currentPrice = in.readFloat();
        this.initialPrice = in.readFloat();
        this.lastRebalanced = new Date(in.readLong());
        this.balanced = in.readBoolean();
        this.currentUnitPriceDate = new Date(in.readLong());
        this.percentageChangeLimit = in.readInt();
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Portfolio> CREATOR = new Creator<Portfolio>()
    {
        @Override
        public Portfolio createFromParcel(Parcel in)
        {
            return new Portfolio(in);
        }

        @Override
        public Portfolio[] newArray(int size)
        {
            return new Portfolio[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeList(this.companies);
        dest.writeFloat(this.currentPrice);
        dest.writeFloat(this.initialPrice);
        dest.writeSerializable(this.lastRebalanced);
        dest.writeBoolean(this.balanced);
        dest.writeSerializable(this.currentUnitPriceDate);
        dest.writeInt(this.percentageChangeLimit);
    }

    //-----------------------------Methods-----------------------------

    public float getCurrentPrice()
    {
        float totalPrice = 0;

        for(Company c : this.companies)
        {
            totalPrice += c.getCurrentUnitPrice();
        }

        return totalPrice;
    }

    public float getPriceDifference()
    {
        return 0;
    }

    public void balancePortfolio()
    {

    }

    public Company addCompany()
    {
        return null;
    }

    public void removeCompany(Company c)
    {

    }
}
