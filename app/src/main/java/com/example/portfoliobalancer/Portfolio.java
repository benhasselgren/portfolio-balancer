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
    private double currentPrice;
    private double initialPrice;
    private Date lastRebalanced;
    private boolean balanced;
    private Date currentPriceDate;
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

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
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

    public Date getCurrentPriceDate() {
        return currentPriceDate;
    }

    public void setCurrentPriceDate(Date currentUnitPriceDate) {
        this.currentPriceDate = currentUnitPriceDate;
    }

    public int getPercentageChangeLimit() {
        return percentageChangeLimit;
    }

    public void setPercentageChangeLimit(int percentageChangeLimit) {
        this.percentageChangeLimit = percentageChangeLimit;
    }

    //-----------------------------Constructors-----------------------------

    public Portfolio(String name, String description, List<Company> companies, double currentPrice, double initialPrice, Date lastRebalanced, boolean balanced, Date currentPriceDate, int percentageChangeLimit)
    {
        this.name = name;
        this.description = description;
        this.companies = companies;
        this.currentPrice = currentPrice;
        this.initialPrice = initialPrice;
        this.lastRebalanced = lastRebalanced;
        this.balanced = balanced;
        this.currentPriceDate = currentPriceDate;
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
        this.currentPriceDate = p.currentPriceDate;
        this.percentageChangeLimit = p.percentageChangeLimit;
    }


    protected Portfolio(Parcel in)
    {
        this.name = in.readString();
        this.description = in.readString();
        in.readTypedList(this.companies, Company.CREATOR);
        this.currentPrice = in.readDouble();
        this.initialPrice = in.readDouble();
        this.lastRebalanced = new Date(in.readLong());
        this.balanced = in.readByte() != 0;
        this.currentPriceDate = new Date(in.readLong());
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
        dest.writeTypedList(this.companies);
        dest.writeDouble(this.currentPrice);
        dest.writeDouble(this.initialPrice);
        dest.writeSerializable(this.lastRebalanced);
        dest.writeByte((byte) (this.balanced ? 1 : 0));
        dest.writeSerializable(this.currentPriceDate);
        dest.writeInt(this.percentageChangeLimit);
    }

    //-----------------------------Methods-----------------------------

    public double getCurrentPrice()
    {
        /*
        float totalPrice = 0;

        for(Company c : this.companies)
        {
            totalPrice += c.getCurrentUnitPrice();
        }
    */
        return this.getCurrentPrice();
    }

    public double getPriceDifference()
    {
        return 0;
    }

    public void balancePortfolio()
    {

    }

    public void addCompany(Company c)
    {
        this.companies.add(c);
    }

    public void removeCompany(Company c)
    {
        this.companies.remove(c);
    }
}
