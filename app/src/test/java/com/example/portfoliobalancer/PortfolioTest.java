package com.example.portfoliobalancer;

import com.example.portfoliobalancer.business_logic_classes.Company;
import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test Portfolio methods
 * @see com.example.portfoliobalancer.business_logic_classes.Portfolio
 */
public class PortfolioTest {

    /**
     * Test getCurrentPrice method
     */
    @Test
    public void getCurrentPriceTestNewPortfolio() {

        double expected = 100;

        ArrayList<Company>companies=new ArrayList<Company>();

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);

        double actual = p.getCurrentPrice(true);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void getCurrentPriceTestPortfolio() {

        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        double actual = p.getCurrentPrice(false);

        assertEquals(expected, actual, 0);
    }

    /**
     * Test getTotalPercentage method
     */

    @Test
    public void getTotalPercentageTest() {

        int expected = 100;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        int actual = p.getTotalPercentage();

        assertEquals(expected, actual, 0);
    }

    /**
     * Test getPriceGrowth method
     */
    @Test
    public void getPriceGrowthTestNoGrowth() {

        double expected = 0;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        double actual = p.getPriceGrowth();

        assertEquals(expected, actual, 0);
    }

    @Test
    public void getPriceGrowthTestGrowth() {

        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 10);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 10);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        for(Company c : companies)
        {
            c.setCostPrice(10);
        }

        double actual = p.getPriceGrowth();

        assertEquals(expected, actual, 0);
    }

    /**
     * Test getPercentageGrowth method
     */
    @Test
    public void getPercentageGrowthTestNoGrowth() {

        double expected = 0;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        double actual = p.getPercentageGrowth();

        assertEquals(expected, actual, 0);
    }

    @Test
    public void getPercentageGrowthTestGrowth() {

        double expected = 10;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 10);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 10);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        for(Company c : companies)
        {
            c.setCostPrice(10);
        }

        double actual = p.getPercentageGrowth();

        assertEquals(expected, actual, 0);
    }

    /**
     * Test checkPorfolioisBalanced method
     */
    @Test
    public void balancePortfolioTestNotBalanced() {

        boolean expected = false;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        Company c3 = new Company("Apple", "APPL", 2.5, 10, 50, 10, null, 5);
        Company c4 = new Company("Microsoft", "MSFT", 2.5, 10, 50, 10, null, 5);

        companies.add(c3);
        companies.add(c4);

        p.checkPortfolioIsBalanced(companies);

        boolean actual = p.isBalanced();

        assertEquals(expected, actual);
    }

    @Test
    public void balancePortfolioTestBalanced() {

        boolean expected = true;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        Company c3 = new Company("Apple", "APPL", 2.5, 5, 50, 10, null, 5);
        Company c4 = new Company("Microsoft", "MSFT", 2.5, 5, 50, 10, null, 5);

        companies.add(c3);
        companies.add(c4);

        p.checkPortfolioIsBalanced(companies);

        boolean actual = p.isBalanced();

        assertEquals(expected, actual);
    }
}
