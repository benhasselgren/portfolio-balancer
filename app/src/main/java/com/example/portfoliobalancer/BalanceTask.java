package com.example.portfoliobalancer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.portfoliobalancer.business_logic_classes.Portfolio;

public class BalanceTask extends AsyncTask<Portfolio, Void, Portfolio> {

    private Context context;
    private ProgressDialog progressDialog;

    public BalanceTask(ProgressDialog progressDialog, Context context) {

        this.context = context;
        this.progressDialog = progressDialog;
    }

    @Override
    protected Portfolio doInBackground(Portfolio... portfolios) {
        //Get the portfolio
        Portfolio portfolio = portfolios[0];

        //Balance the portfolio
        portfolio.balancePortfolio();

        //Return the portfolio
        return portfolio;
    }

    // Before the tasks execution
    protected void onPreExecute(){
        super.onPreExecute();
        // Display the progress dialog on async task start
        progressDialog.show();
    }

    // When all async task have finished
    protected void onPostExecute(Portfolio result){
        // Hide the progress dialog
        progressDialog.dismiss();

        // sanity check, whether we actually received the image.
        if(result != null) {

        } else {
            // Something went wrong while downloading the image
            Toast.makeText(context,"Error - could not balance portfolio", Toast.LENGTH_LONG).show();
        }
    }
}
