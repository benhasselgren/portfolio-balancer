package com.example.portfoliobalancer.business_logic_classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

//######################-----------------------------PortfolioClass-----------------------------######################
//Parcelable class that hold details about a portfolio
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

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
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

    public Portfolio(){}

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
        return this.currentPrice;
    }

    public double getPriceDifference()
    {
        return 0;
    }

    public double getGrowth()
    {
        return this.currentPrice - this.initialPrice;
    }

    public void balancePortfolio()
    {
        //Get the current date
        Date date = Calendar.getInstance().getTime();

        //Set the current price date of the portfolio
        this.currentPriceDate = date;

        //Balance the portfolio by diving the portfolios equity among the companies (using the target percentages)
        for(Company c : companies)
        {
            //Get the available amount to invest based on target percentage ( [targetPercentage/100] * current price of portfolio)
            double c_investment_sum = (c.getTargetPercentage()/100.00) * this.currentPrice;

            //Set the unit count by dividing the cost of the company by the available amount to invest
            c.setUnitCount(c_investment_sum/c.getCostPrice());
            //Set the current unit price by calling the getUnitPrice method
            c.setCurrentUnitPrice(c.getCurrentUnitPrice());
            c.setIntitialPrice(c.getCurrentUnitPrice());
            //Set the current unit price date of the company
            c.setCurrentUnitPriceDate(date);
        }

        // Set the portfolio to balanced
        this.setBalanced(true);

        //Set the last rebalanced date of the portfolio to now
        this.setLastRebalanced(date);
    }

    public void addCompany(Company c)
    {
        this.companies.add(c);
    }

    public void removeCompany(Company c)
    {
        this.companies.remove(c);
    }

    public int getTotalPercentage()
    {
        int total = 0;
        for(Company c : companies)
        {
            total += c.getTargetPercentage();
        }
        return total;
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(this.companies);
        dest.writeDouble(this.currentPrice);
        dest.writeDouble(this.initialPrice);
        dest.writeLong(this.lastRebalanced != null ? this.lastRebalanced.getTime() : -1);
        dest.writeByte(this.balanced ? (byte) 1 : (byte) 0);
        dest.writeLong(this.currentPriceDate != null ? this.currentPriceDate.getTime() : -1);
        dest.writeInt(this.percentageChangeLimit);
    }

    protected Portfolio(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.companies = in.createTypedArrayList(Company.CREATOR);
        this.currentPrice = in.readDouble();
        this.initialPrice = in.readDouble();
        long tmpLastRebalanced = in.readLong();
        this.lastRebalanced = tmpLastRebalanced == -1 ? null : new Date(tmpLastRebalanced);
        this.balanced = in.readByte() != 0;
        long tmpCurrentPriceDate = in.readLong();
        this.currentPriceDate = tmpCurrentPriceDate == -1 ? null : new Date(tmpCurrentPriceDate);
        this.percentageChangeLimit = in.readInt();
    }

    public static final Creator<Portfolio> CREATOR = new Creator<Portfolio>() {
        @Override
        public Portfolio createFromParcel(Parcel source) {
            return new Portfolio(source);
        }

        @Override
        public Portfolio[] newArray(int size) {
            return new Portfolio[size];
        }
    };
}
