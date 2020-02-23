package com.example.portfoliobalancer.classes;

public class Validation {

    //-----------------------------Methods-----------------------------

    //Checks all the field to see if they are valid and throws exceptions if they are not valid
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
            int amountInt = Integer.parseInt(amountString);
            if (amountInt < 100 || amountInt > 25000)
            {
                throw new RuntimeException("Amount has to be between £100-£25,000.");
            }
        }
    }
}
