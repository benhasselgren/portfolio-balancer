package com.example.portfoliobalancer.business_logic_classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Company
 * Parcelable class that hold details about a company
 */
public class Company implements Parcelable {

    //-----------------------------Instance variables-----------------------------
    private String name;
    private String companyCode;
    private double unitCount;
    private double costPrice;
    private int targetPercentage;
    private double initialPrice;
    private Date currentUnitPriceDate;
    private double totalAmountAdded;

    //-----------------------------Getters/Setters-----------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public double getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(double unitCount) {
        this.unitCount = unitCount;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(int targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public Date getCurrentUnitPriceDate() {
        return currentUnitPriceDate;
    }

    public void setCurrentUnitPriceDate(Date currentUnitPriceDate) {
        this.currentUnitPriceDate = currentUnitPriceDate;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getTotalAmountAdded() {
        return totalAmountAdded;
    }

    public void setTotalAmountAdded(double totalAmountAdded) {
        this.totalAmountAdded = totalAmountAdded;
    }

    //-----------------------------Constructors-----------------------------

    /**
     * Company()
     * Creates an empty Company object
     */
    public Company()
    {
    }

    /**
     * Company()
     * Creates a company that can be used for tracking companies cost prices
     */
    public Company(String name, String companyCode, double costPrice)
    {
        this.name = name;
        this.companyCode = companyCode;
        this.costPrice = costPrice;
    }

    /**
     * Company()
     * Creates a company object with all fields assigned to
     */
    public Company(String name, String companyCode, double unitCount, double costPrice, int targetPercentage, Date currentUnitPriceDate, double initialPrice)
    {
        this.name = name;
        this.companyCode = companyCode;
        this.unitCount = unitCount;
        this.costPrice = costPrice;
        this.targetPercentage = targetPercentage;
        this.currentUnitPriceDate = currentUnitPriceDate;
        this.initialPrice = initialPrice;
    }

    /**
     * Company()
     * Creates a company object from another company object.
     */
    public Company(Company c)
    {
        this.name = c.name;
        this.companyCode = c.companyCode;
        this.unitCount = c.unitCount;
        this.costPrice = c.costPrice;
        this.targetPercentage = c.targetPercentage;
        this.currentUnitPriceDate = c.currentUnitPriceDate;
        this.initialPrice = c.initialPrice;
    }

    //-----------------------------Methods-----------------------------

    /**
     * removeAmountFromTotalAmountAdded()
     * Removes a given value from the totalAmountAdded variable
     * @param amount
     */
    public void removeAmountFromTotalAmountAdded(double amount)
    {
        this.totalAmountAdded -= amount;
    }

    /**
     * addAmountFromTotalAmountAdded()
     * Adds a given value from the totalAmountAdded variable
     * @param amount
     */
    public void addAmountToTotalAmountAdded(double amount)
    {
        this.totalAmountAdded += amount;
    }

    /**
     * getCurrentUnitPrice()
     * Multiplies the unit count of the company by the price of a unit
     * @return the current unit price of the company
     */
    public double getCurrentUnitPrice() {

        return this.costPrice * this.unitCount;
    }

    /**
     * getPriceGrowth()
     * Calculates the price growth of a company
     * Eg. Initial price of a stock in company was £100. Current price is now £150. So price growth equals £50
     * @return the growth price of the company
     */
    public double getPriceGrowth()
    {
        return (this.getCurrentUnitPrice() - (this.initialPrice * this.unitCount)) - this.totalAmountAdded;
    }

    /**
     * getPercentageGrowth()
     * Calculates the percentage growth of a company
     * Eg. Initial price of a stock in company was £100. Current price is now £150. So percentage growth equals 50%
     * @return the growth percentage of the company
     */
    public double getPercentageGrowth()
    {
        double growth = getPriceGrowth()/((this.initialPrice )* this.unitCount)*100;
        return growth;
    }

    /**
     * toString()
     * Eg. Initial price of a stock in company was £100. Current price is now £150. So percentage growth equals 50%
     * @return a string that is used to display the name of the company and it's company code. (Used in AddCompanyActivity)
     * @see com.example.portfoliobalancer.add_company_activity.AddCompanyActivity
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.companyCode);
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.companyCode);
        dest.writeDouble(this.unitCount);
        dest.writeDouble(this.costPrice);
        dest.writeInt(this.targetPercentage);
        dest.writeDouble(this.initialPrice);
        dest.writeLong(this.currentUnitPriceDate != null ? this.currentUnitPriceDate.getTime() : -1);
        dest.writeDouble(this.totalAmountAdded);
    }

    protected Company(Parcel in) {
        this.name = in.readString();
        this.companyCode = in.readString();
        this.unitCount = in.readDouble();
        this.costPrice = in.readDouble();
        this.targetPercentage = in.readInt();
        this.initialPrice = in.readDouble();
        long tmpCurrentUnitPriceDate = in.readLong();
        this.currentUnitPriceDate = tmpCurrentUnitPriceDate == -1 ? null : new Date(tmpCurrentUnitPriceDate);
        this.totalAmountAdded = in.readDouble();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel source) {
            return new Company(source);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
}
