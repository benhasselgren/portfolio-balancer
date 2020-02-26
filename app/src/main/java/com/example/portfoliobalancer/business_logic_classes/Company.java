package com.example.portfoliobalancer.business_logic_classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

//######################-----------------------------CompanyClass-----------------------------######################
//Parcelable class that hold details about a company
public class Company implements Parcelable {

    //-----------------------------Instance variables-----------------------------
    private String name;
    private String companyCode;
    private double unitCount;
    private double costPrice;
    private int targetPercentage;
    private double currentUnitPrice;
    private double intitialPrice;
    private Date currentUnitPriceDate;

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

    public void setCurrentUnitPrice(double currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public double getIntitialPrice() {
        return intitialPrice;
    }

    public void setIntitialPrice(double intitialPrice) {
        this.intitialPrice = intitialPrice;
    }

    //-----------------------------Constructors-----------------------------

    public Company()
    {
    }

    public Company(String name, String companyCode, double unitCount, double costPrice, int targetPercentage, double currentUnitPrice, Date currentUnitPriceDate, double intitialPrice)
    {
        this.name = name;
        this.companyCode = companyCode;
        this.unitCount = unitCount;
        this.costPrice = costPrice;
        this.targetPercentage = targetPercentage;
        this.currentUnitPrice = currentUnitPrice;
        this.currentUnitPriceDate = currentUnitPriceDate;
        this.intitialPrice = intitialPrice;
    }

    public Company(Company c)
    {
        this.name = c.name;
        this.companyCode = c.companyCode;
        this.unitCount = c.unitCount;
        this.costPrice = c.costPrice;
        this.targetPercentage = c.targetPercentage;
        this.currentUnitPrice = c.currentUnitPrice;
        this.currentUnitPriceDate = c.currentUnitPriceDate;
        this.intitialPrice = c.intitialPrice;
    }

    //-----------------------------Methods-----------------------------

    public double getCurrentUnitPrice() {

        return this.costPrice * this.unitCount;
    }

    public double getGrowth()
    {
        return this.currentUnitPrice - this.intitialPrice;
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
        dest.writeDouble(this.currentUnitPrice);
        dest.writeDouble(this.intitialPrice);
        dest.writeLong(this.currentUnitPriceDate != null ? this.currentUnitPriceDate.getTime() : -1);
    }

    protected Company(Parcel in) {
        this.name = in.readString();
        this.companyCode = in.readString();
        this.unitCount = in.readDouble();
        this.costPrice = in.readDouble();
        this.targetPercentage = in.readInt();
        this.currentUnitPrice = in.readDouble();
        this.intitialPrice = in.readDouble();
        long tmpCurrentUnitPriceDate = in.readLong();
        this.currentUnitPriceDate = tmpCurrentUnitPriceDate == -1 ? null : new Date(tmpCurrentUnitPriceDate);
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
