package com.example.portfoliobalancer;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test Company methods
 * @see com.example.portfoliobalancer.business_logic_classes.Company
 */
public class CompanyTest {

    /**
     * Test getCurrentUnitPrice method
     */
    @Test
    public void getCurrentUnitPriceTest()
    {
        double expectedValue = 12.5;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        double actualValue = c1.getCurrentUnitPrice();

        assertEquals(expectedValue, actualValue, 0);
    }

    /**
     * Test getPriceGrowth method
     */
    @Test
    public void getPriceGrowthIncreaseTest()
    {
        double expectedValue = 5;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        //Increase cost price by £5
        c1.setCostPrice(10);

        double actualValue = c1.getPriceGrowth();

        assertEquals(expectedValue, actualValue, 0);
    }

    @Test
    public void getPriceGrowthDecreaseTest()
    {
        double expectedValue = -2.5;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        //Decrease cost price by £2.5
        c1.setCostPrice(2.5);

        double actualValue = c1.getPriceGrowth();

        assertEquals(expectedValue, actualValue, 0);
    }

    /**
     * Test getPriceGrowth method
     */
    @Test
    public void getPercentageGrowthIncreaseTest()
    {
        double expectedValue = 100.0;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        //Increase cost price by £5
        c1.setCostPrice(10);

        double actualValue = c1.getPercentageGrowth();

        assertEquals(expectedValue, actualValue, 0);
    }

    @Test
    public void getPercentageGrowthDecreaseTest()
    {
        double expectedValue = -50;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        //Decrease cost price by £2.5
        c1.setCostPrice(2.5);

        double actualValue = c1.getPercentageGrowth();

        assertEquals(expectedValue, actualValue, 0);
    }

    /**
     * Test toString method
     */
    @Test
    public void toStringTest()
    {
        String expectedValue = "Apple (APPL)";
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);

        //Get string
        String actualValue = c1.toString();

        assertEquals(expectedValue, actualValue);
    }
}
