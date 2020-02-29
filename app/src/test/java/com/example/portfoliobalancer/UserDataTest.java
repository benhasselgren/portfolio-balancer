package com.example.portfoliobalancer;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;
import com.example.portfoliobalancer.business_logic_classes.UserData;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test UserData methods
 * @see com.example.portfoliobalancer.business_logic_classes.UserData
 */
public class UserDataTest {

    /**
     * Test addPortfolio method
     */
    @Test
    public void addPortfolioTestNull()
    {
        int expectedValue = 0;
        UserData ud = new UserData();

        ud.addPortfolio(null);

        int actualValue = ud.getPortfolios().size();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void addPortfolioTest()
    {
        int expectedSizeValue = 1;
        int expectedIDValue = 1;
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        p.setId(1);

        ud.addPortfolio(p);

        int actualSizeValue = ud.getPortfolios().size();
        int actualIDValue = ud.findPortfolioById(1).getId();

        assertEquals(expectedSizeValue, actualSizeValue);
        assertEquals(expectedIDValue, actualIDValue);
    }

    /**
     * Test removePortfolio method
     */
    @Test
    public void removePortfolioTestNull()
    {
        int expectedValue = 1;
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        ud.addPortfolio(p);

        ud.removePortfolio(null);

        int actualValue = ud.getPortfolios().size();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void removePortfolioTest()
    {
        int expectedSizeValue = 0;
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        p.setId(1);

        ud.addPortfolio(p);

        ud.removePortfolio(p);

        int actualSizeValue = ud.getPortfolios().size();

        assertEquals(expectedSizeValue, actualSizeValue);
    }

    /**
     * Test removePortfolio method
     */
    @Test
    public void updatePortfolioTestNull()
    {
        String expectedValue = "TestPortfolio";
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        ud.addPortfolio(p);

        Portfolio updatedP = null;

        ud.updatePortfolio(null, p);

        String actualValue = ud.findPortfolioById(1).getName();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void updatePortfolioTest()
    {
        String expectedValue = "I am updated";
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        ud.addPortfolio(p);

        Portfolio updatedP = p;


        updatedP.setName("I am updated");

        ud.updatePortfolio(updatedP, p);

        String actualValue = ud.findPortfolioById(1).getName();

        assertEquals(expectedValue, actualValue);
    }

    /**
     * Test findPortfolioById method
     */
    @Test
    public void findPortfolioByIdNullDoesNotExist()
    {
        Portfolio expectedPortfolio = null;
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);
        Portfolio p1 = new Portfolio(2, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        ud.addPortfolio(p);
        ud.addPortfolio(p1);

        Portfolio actualPortfolio = ud.findPortfolioById(3);

        assertEquals(expectedPortfolio, actualPortfolio);
    }

    @Test
    public void findPortfolioByIdDoesExist()
    {
        int expectedIDValue = 2;
        UserData ud = new UserData();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, 5, null, true, null, 10);
        Portfolio p1 = new Portfolio(2, "TestPortfolio", "test description", null, 5, null, true, null, 10);

        ud.addPortfolio(p);
        ud.addPortfolio(p1);

        Portfolio foundP = ud.findPortfolioById(2);

        int actualIDValue = foundP.getId();

        assertEquals(expectedIDValue, actualIDValue);
    }

    /**
     * Test checkPortfoliosAreBalanced method
     */
    @Test
    public void checkPortfoliosAreBalanced()
    {
        boolean expectedPortfolioOneValue = true;
        boolean expectedPortfolioTwoValue = false;
        UserData ud = new UserData();
        ArrayList<Company>updatedCompanies=new ArrayList<Company>();
        ArrayList<Company>companiesPortfolioOne=new ArrayList<Company>();
        ArrayList<Company>companiesPortfolioTwo=new ArrayList<Company>();

        //Add companies to portfolio one
        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        companiesPortfolioOne.add(c1);
        companiesPortfolioOne.add(c2);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companiesPortfolioOne, 5, null, true, null, 10);

        //Add companies to portfolio two
        Company c3 = new Company("Tesla", "TSLA", 2.5, 100, 50, null, 5);
        Company c4 = new Company("Dog", "DOG", 2.5, 100, 50, null, 5);

        companiesPortfolioTwo.add(c3);
        companiesPortfolioTwo.add(c4);

        Portfolio p1 = new Portfolio(2, "TestPortfolio", "test description", companiesPortfolioTwo, 5, null, true, null, 10);

        //Add portfolios to user data
        ud.addPortfolio(p);
        ud.addPortfolio(p1);

        //Update company prices and call method
        Company c5 = new Company("Apple", "APPL", 2.5, 10, 50, null, 5);
        Company c6 = new Company("Microsoft", "MSFT", 2.5, 10, 50, null, 5);
        Company c7 = new Company("Tesla", "TSLA", 2.5, 101, 50, null, 5);
        Company c8 = new Company("Dog", "DOG", 2.5, 100, 50, null, 5);

        updatedCompanies.add(c5);
        updatedCompanies.add(c6);
        updatedCompanies.add(c7);
        updatedCompanies.add(c8);

        ud.checkPortfoliosAreBalanced(updatedCompanies);

        //Track percentage changes of company
        for(Portfolio portfolio : ud.getPortfolios())
        {
            for (Company c : p.getCompanies())
            {
                System.out.println("Percentage change p: " + c.getPercentageChange());
            }
        }


        boolean actualPortfolioOneValue = ud.findPortfolioById(1).isBalanced();
        boolean actualPortfolioTwoValue = ud.findPortfolioById(2).isBalanced();
        assertEquals(expectedPortfolioOneValue, actualPortfolioOneValue);
        assertEquals(expectedPortfolioTwoValue, actualPortfolioTwoValue);
    }

    @Test
    public void checkPortfoliosAreBalancedNull()
    {
        UserData ud = new UserData();

        ud.checkPortfoliosAreBalanced(null);
    }
}
