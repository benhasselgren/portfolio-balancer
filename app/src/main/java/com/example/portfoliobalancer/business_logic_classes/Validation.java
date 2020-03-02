package com.example.portfoliobalancer.business_logic_classes;

/**
 * Validation
 * Class that hold validation methods
 */

public class Validation {

    //-----------------------------Methods-----------------------------

    /**
     * checkPortfolioDetailsValid
     * Check details entered by user are valid. Throws exception if they are not.
     * Used in PortfolioSettingsActivity and AddPortfolioActivity.
     * @see com.example.portfoliobalancer.portfolio_settings_activity.PortfolioSettingsActivity
     * @see com.example.portfoliobalancer.add_portfolio_activity.AddPortfolioActivity
     * @param nameString
     * @param descriptionString
     * @param amountString
     */
    public void checkPortfolioDetailsValid(String nameString, String descriptionString, String amountString)
    {
        if (nameString.isEmpty())
        {
            throw new RuntimeException("Name field cannot be empty.");
        }
        else if(descriptionString.isEmpty())
        {
            throw new RuntimeException("Description field cannot be empty.");
        }
        else if(amountString.isEmpty())
        {
            throw new RuntimeException("Amount field cannot be empty.");
        }
        else {
            if(amountString.matches("[0-9]+")) {
                int amountInt = Integer.parseInt(amountString);
                if (amountInt < 100 || amountInt > 25000) {
                    throw new RuntimeException("Amount has to be between £100-£25,000.");
                }
            }
            else
            {
                throw new RuntimeException("Amount has to be a number.");
            }
        }
    }

    public void checkCompanyDetailsValid(String codeString, String nameString, String priceString)
    {
        if (codeString.isEmpty())
        {
            throw new RuntimeException("Code field cannot be empty.");
        }
        else if(nameString.isEmpty())
        {
            throw new RuntimeException("Name field cannot be empty.");
        }
        else if(priceString.isEmpty())
        {
            throw new RuntimeException("Price field cannot be empty.");
        }
        else {
            if(priceString.matches("[0-9]+")) {
                int priceInt = Integer.parseInt(priceString);
                if (priceInt < 0 || priceInt > 5000) {
                    throw new RuntimeException("Amount has to be between £0-£5,000.");
                }
            }
            else
            {
                throw new RuntimeException("Amount has to be a number.");
            }
        }
    }
}
