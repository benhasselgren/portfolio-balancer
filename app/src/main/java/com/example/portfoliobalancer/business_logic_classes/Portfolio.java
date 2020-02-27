package com.example.portfoliobalancer.business_logic_classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.lang.Math;

//######################-----------------------------PortfolioClass-----------------------------######################
//Parcelable class that hold details about a portfolio
public class Portfolio implements Parcelable
{
    //-----------------------------Instance variables-----------------------------
    private int id;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Portfolio(int id, String name, String description, List<Company> companies, double currentPrice, double initialPrice, Date lastRebalanced, boolean balanced, Date currentPriceDate, int percentageChangeLimit)
    {
        this.id = id;
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
        this.id = p.id;
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

    public void addCompany(Company c)
    {
        this.companies.add(c);
    }

    public void removeCompany(Company c)
    {
        this.companies.remove(c);
    }

    public double getCurrentPrice(Boolean newPortfolio)
    {
        if(newPortfolio)
        {
            return this.currentPrice;
        }
        else
        {
            float totalPrice = 0;

            for(Company c : this.companies)
            {
                totalPrice += c.getCurrentUnitPrice();
            }

            return totalPrice;
        }
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

    public double getPriceGrowth()
    {
        return this.currentPrice - this.initialPrice;
    }

    public double getPercentageGrowth()
    {
        double growth = ((this.currentPrice/this.initialPrice)-1)*10;
        return growth;
    }

    public void balancePortfolio(boolean newPortfolio)
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
            //Set the intitial price to the current unit price if it's a new portfolio
            if(newPortfolio)
            {
                c.setInitialPrice(c.getCurrentUnitPrice());
            }
            //Set the current unit price date of the company
            c.setCurrentUnitPriceDate(date);
        }

        // Set the portfolio to balanced
        this.setBalanced(true);

        //Set the last rebalanced date of the portfolio to now
        this.setLastRebalanced(date);
    }

    public void checkPortfolioIsBalanced(List<Company> updatedCompanies)
    {
        int totalPercentageChange = 0;
        for(Company c : this.companies) {

            //Find the updated company price and set the company costPrice to the updated price
            for(Company updatedCompany : updatedCompanies)
            {
                if (c.getCompanyCode().equals(updatedCompany.getCompanyCode()))
                {
                    c.setCostPrice(updatedCompany.getCostPrice());
                    break;
                }
            }
            //Calculate the current percentage of the portfolio that company takes
            double currentPercentage = (c.getCurrentUnitPrice() / getCurrentPrice(false)) * 100;
            //Calculate the difference between the target percentage and the current percentage and add it to totalPercentageChange
            totalPercentageChange += Math.abs(c.getTargetPercentage() - currentPercentage);
        }

        //If the total percentage change is greater than the percentage change limit, then set balanced to false
        if(totalPercentageChange > this.percentageChangeLimit)
        {
            this.balanced = false;
        }
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
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
        this.id = in.readInt();
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
