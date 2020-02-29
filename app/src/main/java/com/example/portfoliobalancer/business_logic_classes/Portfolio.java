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
    private double initialPrice;
    private Date lastRebalanced;
    private boolean balanced;
    private Date currentPriceDate;
    private int percentageChangeLimit;
    private double totalAmountAdded;

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

    public double getTotalAmountAdded() {
        return totalAmountAdded;
    }

    public void setTotalAmountAdded(double totalAmountAdded) {
        this.totalAmountAdded = totalAmountAdded;
    }

    //-----------------------------Constructors-----------------------------

    public Portfolio(){}

    public Portfolio(int id, String name, String description, List<Company> companies, double initialPrice, Date lastRebalanced, boolean balanced, Date currentPriceDate, int percentageChangeLimit)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companies = companies;
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

    public void removeAmountFromTotalAmountAdded(double amount)
    {
        this.totalAmountAdded -= amount;
    }

    public void addAmountToTotalAmountAdded(double amount)
    {
        this.totalAmountAdded += amount;
    }

    public double getCurrentPrice(Boolean newPortfolio)
    {
        if(newPortfolio)
        {
            return this.initialPrice;
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
        //Price growth = current price of portfolio - (the initial price plus the amount added to the portfolio (or removed))
        return this.getCurrentPrice(false) - (this.initialPrice + this.totalAmountAdded);
    }

    public double getPercentageGrowth()
    {
        double growth = (getPriceGrowth()/this.initialPrice)*100;
        return growth;
    }

    public void balancePortfolio(boolean newPortfolio, double updatedAmount)
    {
        //Get the current date
        Date date = Calendar.getInstance().getTime();
        //Get the current unitPrice of portfolio
        double p_currentUnitPrice;
        if(newPortfolio) {
            p_currentUnitPrice = this.getCurrentPrice(true);
        }
        else
        {
            p_currentUnitPrice = this.getCurrentPrice(false);

            //If the portfolio has been updated in the portfolios settings, then check if amount has been altered
            //If money has been added or removed then update the current unit price of the portfolio
            //If money has been added or removed then add or remove the amount to totalAmountAdded variable
            if(updatedAmount != 0 && updatedAmount > p_currentUnitPrice)
            {
                double diff = updatedAmount - p_currentUnitPrice;
                this.addAmountToTotalAmountAdded(diff);
                p_currentUnitPrice = updatedAmount;
            }
            else if(updatedAmount != 0 && updatedAmount < p_currentUnitPrice)
            {
                double diff = p_currentUnitPrice - updatedAmount;
                this.removeAmountFromTotalAmountAdded(diff);
                p_currentUnitPrice = updatedAmount;
            }
        }

        //Set the current price date of the portfolio
        this.currentPriceDate = date;
        //Declare the company investment sum
        double c_investment_sum;

        //Balance the portfolio by diving the portfolios equity among the companies (using the target percentages)
        for(Company c : companies)
        {
            //Get the available amount to invest based on target percentage ( [targetPercentage/100] * current price of portfolio)
            c_investment_sum = (c.getTargetPercentage() / 100.00) * p_currentUnitPrice;

            //Set the unit count by dividing the cost of the company by the available amount to invest
            c.setUnitCount(c_investment_sum/c.getCostPrice());

            //Set the current unit price date of the company
            c.setCurrentUnitPriceDate(date);

            //Set the percentage change to 0%
            c.setPercentageChange(0);
        }

        // Set the portfolio to balanced
        this.setBalanced(true);

        //Set the last rebalanced date of the portfolio to now
        this.setLastRebalanced(date);
    }

    public void updatePortfolio(List<Company> updatedCompanies)
    {
        if(updatedCompanies!=null)
        {
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
            }
        }
    }

    public void checkPortfolioIsBalanced(List<Company> updatedCompanies)
    {
        //Update portfolio
        updatePortfolio(updatedCompanies);

        double totalPercentageChange = 0;

        //Get the current price of the portfolio
        double p_current_price = getCurrentPrice(false);

        for(Company c : this.companies) {
            //Calculate the current percentage of the portfolio that company takes
            double currentPercentage = (c.getCurrentUnitPrice() / p_current_price) * 100;
            //Calculate the difference between the target percentage and the current percentage and add it to totalPercentageChange
            totalPercentageChange += Math.abs(c.getTargetPercentage() - currentPercentage);
            //Set the percentage change for the company
            c.setPercentageChange((int)Math.abs(c.getTargetPercentage() - currentPercentage));
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
        dest.writeDouble(this.initialPrice);
        dest.writeLong(this.lastRebalanced != null ? this.lastRebalanced.getTime() : -1);
        dest.writeByte(this.balanced ? (byte) 1 : (byte) 0);
        dest.writeLong(this.currentPriceDate != null ? this.currentPriceDate.getTime() : -1);
        dest.writeInt(this.percentageChangeLimit);
        dest.writeDouble(this.totalAmountAdded);
    }

    protected Portfolio(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.companies = in.createTypedArrayList(Company.CREATOR);
        this.initialPrice = in.readDouble();
        long tmpLastRebalanced = in.readLong();
        this.lastRebalanced = tmpLastRebalanced == -1 ? null : new Date(tmpLastRebalanced);
        this.balanced = in.readByte() != 0;
        long tmpCurrentPriceDate = in.readLong();
        this.currentPriceDate = tmpCurrentPriceDate == -1 ? null : new Date(tmpCurrentPriceDate);
        this.percentageChangeLimit = in.readInt();
        this.totalAmountAdded = in.readDouble();
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
