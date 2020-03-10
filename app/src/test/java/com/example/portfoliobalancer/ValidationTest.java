package com.example.portfoliobalancer;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;
import com.example.portfoliobalancer.business_logic_classes.Validation;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test Validation methods
 * @see com.example.portfoliobalancer.business_logic_classes.Validation
 */
public class ValidationTest {

    /**
     * Test checkPortfolioDetailsValid method
     */

    @Test
    public void checkPortfolioDetailsValidNameEmptyTest()
    {
        Validation v = new Validation();
        String name = "";
        String description = "Description";
        String amount = "Amount";
        String expectedValue = "Name field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkPortfolioDetailsValid(name, description, amount);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkPortfolioDetailsValidDescriptionEmptyTest()
    {
        Validation v = new Validation();
        String name = "Name";
        String description = "";
        String amount = "Amount";
        String expectedValue = "Description field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkPortfolioDetailsValid(name, description, amount);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkPortfolioDetailsValidAmountEmptyTest()
    {
        Validation v = new Validation();
        String name = "Name";
        String description = "Description";
        String amount = "";
        String expectedValue = "Amount field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkPortfolioDetailsValid(name, description, amount);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkPortfolioDetailsValidNumberOutOfRange()
    {
        Validation v = new Validation();
        String name = "Name";
        String description = "Description";
        String amount = "10000001";
        String expectedValue = "Amount has to be between £0-£10,000,000.";
        String actualValue = "";

        try
        {
            v.checkPortfolioDetailsValid(name, description, amount);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkPortfolioDetailsValidNumberIsString()
    {
        Validation v = new Validation();
        String name = "Name";
        String description = "Description";
        String amount = "afasfasf";
        String expectedValue = "Amount has to be a number.";
        String actualValue = "";

        try
        {
            v.checkPortfolioDetailsValid(name, description, amount);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    /**
     * Test checkCompanyDetailsValid method
     */

    @Test
    public void checkCompanyDetailsValidCodeEmptyTest()
    {
        Validation v = new Validation();
        String code = "";
        String name = "Name";
        String price = "Amount";
        String expectedValue = "Code field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkCompanyDetailsValid(code, name, price);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkCompanyDetailsValidNameEmptyTest()
    {
        Validation v = new Validation();
        String code = "CODE";
        String name = "";
        String price = "Amount";
        String expectedValue = "Name field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkCompanyDetailsValid(code, name, price);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkCompanyDetailsValidPriceEmptyTest()
    {
        Validation v = new Validation();
        String code = "CODE";
        String name = "Name";
        String price = "";
        String expectedValue = "Price field cannot be empty.";
        String actualValue = "";

        try
        {
            v.checkCompanyDetailsValid(code, name, price);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkCompanyDetailsValidNumberOutOfRange()
    {
        Validation v = new Validation();
        String code = "CODE";
        String name = "Name";
        String price = "6100";
        String expectedValue = "Amount has to be between £0-£5,000.";
        String actualValue = "";

        try
        {
            v.checkCompanyDetailsValid(code, name, price);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void checkCompanyDetailsValidNumberIsString()
    {
        Validation v = new Validation();
        String code = "CODE";
        String name = "Name";
        String price = "fdfhsdjss";
        String expectedValue = "Amount has to be a number.";
        String actualValue = "";

        try
        {
            v.checkCompanyDetailsValid(code, name, price);
        }
        catch(RuntimeException e)
        {
            actualValue = e.getMessage();
        }

        assertEquals(expectedValue, actualValue);
    }
}
