package com.example.portfoliobalancer.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Company implements Parcelable {

    //-----------------------------Instance variables-----------------------------
    private String name;
    private String companyCode;
    private double unitCount;
    private double costPrice;
    private double targetPercentage;
    private double currentUnitPrice;
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

    public double getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public Date getCurrentUnitPriceDate() {
        return currentUnitPriceDate;
    }

    public void setCurrentUnitPriceDate(Date currentUnitPriceDate) {
        this.currentUnitPriceDate = currentUnitPriceDate;
    }


    //-----------------------------Constructors-----------------------------

    public Company(String name, String companyCode, double unitCount, double costPrice, double targetPercentage, double currentUnitPrice, Date currentUnitPriceDate)
    {
        this.name = name;
        this.companyCode = companyCode;
        this.unitCount = unitCount;
        this.costPrice = costPrice;
        this.targetPercentage = targetPercentage;
        this.currentUnitPrice = currentUnitPrice;
        this.currentUnitPriceDate = currentUnitPriceDate;
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
    }

    protected Company(Parcel in)
    {
        this.name = in.readString();
        this.companyCode = in.readString();
        this.unitCount = in.readDouble();
        this.costPrice = in.readDouble();
        this.targetPercentage = in.readDouble();
        this.currentUnitPrice = in.readDouble();
        this.currentUnitPriceDate = new Date(in.readLong());
    }

    //-----------------------------Implemented Parcelable Constructor/Methods-----------------------------
    public static final Creator<Company> CREATOR = new Creator<Company>()
    {
        @Override
        public Company createFromParcel(Parcel in)
        {
            return new Company(in);
        }
        @Override
        public Company[] newArray(int size)
        {
            return new Company[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.name);
        dest.writeString(this.companyCode);
        dest.writeDouble(this.unitCount);
        dest.writeDouble(this.costPrice);
        dest.writeDouble(this.targetPercentage);
        dest.writeDouble(this.currentUnitPrice);
        dest.writeSerializable(this.currentUnitPriceDate);
    }

    //-----------------------------Methods-----------------------------

    public double getCurrentUnitPrice() {

        return this.costPrice * this.unitCount;
    }
}