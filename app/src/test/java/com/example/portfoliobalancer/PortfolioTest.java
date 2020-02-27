package com.example.portfoliobalancer;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

import org.junit.Test;

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

        double expectedValue = 100;

        Portfolio p = new Portfolio(1, "TestPortfolio", "test description", null, expectedValue, expectedValue, null, true, null, 10);

        double actualValue = p.getCurrentPrice(true);

        assertEquals(p);
    }
}
