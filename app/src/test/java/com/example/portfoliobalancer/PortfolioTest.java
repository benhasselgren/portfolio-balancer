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
 * Test Portfolio methods
 * @see com.example.portfoliobalancer.business_logic_classes.Portfolio
 */
public class PortfolioTest {

    /**
     * Test addCompany method
     */
    @Test
    public void addCompanyTestNull()
    {
        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.addCompany(null);
    }

    @Test
    public void addCompanyTest()
    {
        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.addCompany(c1);
    }

    /**
     * Test removeCompany method
     */
    @Test
    public void removeCompanyTestNull()
    {
        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.removeCompany(null);
    }

    @Test
    public void removeCompanyTest()
    {
        double expected = 25;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, expected, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.removeCompany(c1);
    }

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

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

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

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

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

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

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

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

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

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        double actual = p.getPercentageGrowth();

        assertEquals(expected, actual, 0);
    }

    @Test
    public void getPercentageGrowthTestGrowth() {

        double expected = 100;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);
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
     * Test checkPorfolioIsBalanced method
     */
    @Test
    public void checkPortfolioBalancedTestNull() {
        boolean expected = true;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.checkPortfolioIsBalanced(null);

        boolean actual = p.isBalanced();

        assertEquals(expected, actual);
    }


    @Test
    public void checkPortfolioBalancedTestNotBalanced() {

        boolean expected = false;
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        Company c3 = new Company("Apple", "APPL", 2.5, 20, 50, null, 5);
        Company c4 = new Company("Microsoft", "MSFT", 2.5, 20, 50, null, 5);

        companies.add(c3);
        companies.add(c4);

        p.checkPortfolioIsBalanced(companies);

        boolean actual = p.isBalanced();

        assertEquals(expected, actual);
    }

    @Test
    public void checkPortfolioBalancedTestBalanced() {

        boolean expected = true;
        ArrayList<Company>updatedCompanies=new ArrayList<Company>();
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c2 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, true, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        Company c3 = new Company("Apple", "APPL", 2.5, 5, 50, null, 5);
        Company c4 = new Company("Microsoft", "MSFT", 2.5, 5, 50, null, 5);

        updatedCompanies.add(c3);
        updatedCompanies.add(c4);

        p.checkPortfolioIsBalanced(updatedCompanies);

        boolean actual = p.isBalanced();

        assertEquals(expected, actual);
    }

    /**
     * Test balancePortfolio method
     */
    @Test
    public void balancePortfolioNewTestBalanced() {

        boolean expectedBalanced = true;
        double expectedUnitCount = 2.5;
        double expectedInitialPrice = 5;
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String expectedDate = formatter.format(date);

        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 0, 5, 50, null, 0);
        Company c2 = new Company("Microsoft", "MSFT", 0, 5, 50, null, 0);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, false, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.balancePortfolio(true, 0);

        boolean actualBalanced = p.isBalanced();
        double actualUnitCount = p.getCompanies().get(0).getUnitCount();
        double actualInitialPrice = p.getCompanies().get(0).getInitialPrice();
        Date date2 = p.getCompanies().get(0).getCurrentUnitPriceDate();
        String actualDate = formatter.format(date2);

        assertEquals(expectedBalanced, actualBalanced);
        assertEquals(expectedUnitCount, actualUnitCount, 0);
        assertEquals(expectedInitialPrice, actualInitialPrice, 0);
        assertEquals(expectedDate, actualDate);

    }

    @Test
    public void balancePortfolioTestBalanced() {

        boolean expectedBalanced = true;
        double expectedUnitCount = 1.25;
        double expectedInitialPrice = 12.5;
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String expectedDate = formatter.format(date);
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2, 10, 50, null, 12.5);
        Company c2 = new Company("Microsoft", "MSFT", 1, 5, 50, null, 12.5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, false, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.balancePortfolio(false,  0);

        boolean actualBalanced = p.isBalanced();
        double actualUnitCount = p.getCompanies().get(0).getUnitCount();
        double actualInitialPrice = p.getCompanies().get(0).getInitialPrice();
        Date date2 = p.getCompanies().get(0).getCurrentUnitPriceDate();
        String actualDate = formatter.format(date2);

        assertEquals(expectedBalanced, actualBalanced);
        assertEquals(expectedUnitCount, actualUnitCount, 0);
        assertEquals(expectedInitialPrice, actualInitialPrice, 0);
        assertEquals(expectedDate, actualDate);

    }

    @Test
    public void balancePortfolioAddedAmountTestBalanced() {

        boolean expectedBalanced = true;
        double expectedUnitCount = 1.25;
        double expectedInitialPrice = 12.5;
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String expectedDate = formatter.format(date);
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2, 10, 50, null, 12.5);
        Company c2 = new Company("Microsoft", "MSFT", 1, 5, 50, null, 12.5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, false, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.balancePortfolio(false,  0);

        boolean actualBalanced = p.isBalanced();
        double actualUnitCount = p.getCompanies().get(0).getUnitCount();
        double actualInitialPrice = p.getCompanies().get(0).getInitialPrice();
        Date date2 = p.getCompanies().get(0).getCurrentUnitPriceDate();
        String actualDate = formatter.format(date2);

        assertEquals(expectedBalanced, actualBalanced);
        assertEquals(expectedUnitCount, actualUnitCount, 0);
        assertEquals(expectedInitialPrice, actualInitialPrice, 0);
        assertEquals(expectedDate, actualDate);

    }

    @Test
    public void balancePortfolioRemovedAmountTestBalanced() {

        boolean expectedBalanced = true;
        double expectedUnitCount = 1.25;
        double expectedInitialPrice = 12.5;
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        String expectedDate = formatter.format(date);
        ArrayList<Company>companies=new ArrayList<Company>();

        Company c1 = new Company("Apple", "APPL", 2, 10, 50, null, 12.5);
        Company c2 = new Company("Microsoft", "MSFT", 1, 5, 50, null, 12.5);

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", companies, 25, null, false, null, 10);
        p.addCompany(c1);
        p.addCompany(c2);

        p.balancePortfolio(false,  0);

        boolean actualBalanced = p.isBalanced();
        double actualUnitCount = p.getCompanies().get(0).getUnitCount();
        double actualInitialPrice = p.getCompanies().get(0).getInitialPrice();
        Date date2 = p.getCompanies().get(0).getCurrentUnitPriceDate();
        String actualDate = formatter.format(date2);

        assertEquals(expectedBalanced, actualBalanced);
        assertEquals(expectedUnitCount, actualUnitCount, 0);
        assertEquals(expectedInitialPrice, actualInitialPrice, 0);
        assertEquals(expectedDate, actualDate);

    }
}
