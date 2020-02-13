package com.example.portfoliobalancer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Company implements Parcelable {

    //-----------------------------Instance variables-----------------------------
    private String name;
    private String companyCode;
    private float unitCount;
    private float costPrice;
    private float targetPercentage;
    private float currentUnitPrice;
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

    public float getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(float unitCount) {
        this.unitCount = unitCount;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(float targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public Date getCurrentUnitPriceDate() {
        return currentUnitPriceDate;
    }

    public void setCurrentUnitPriceDate(Date currentUnitPriceDate) {
        this.currentUnitPriceDate = currentUnitPriceDate;
    }


    //-----------------------------Constructors-----------------------------

    public Company(String name, String companyCode, float unitCount, float costPrice, float targetPercentage, float currentUnitPrice, Date currentUnitPriceDate)
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
        this.unitCount = in.readFloat();
        this.costPrice = in.readFloat();
        this.targetPercentage = in.readFloat();
        this.currentUnitPrice = in.readFloat();
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
        dest.writeFloat(this.unitCount);
        dest.writeFloat(this.costPrice);
        dest.writeFloat(this.targetPercentage);
        dest.writeFloat(this.currentUnitPrice);
        dest.writeSerializable(this.currentUnitPriceDate);
    }

    //-----------------------------Methods-----------------------------

    public float getCurrentUnitPrice() {

        return this.costPrice * this.unitCount;
    }
}
