package com.example.portfoliobalancer.companies_prices_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.portfoliobalancer.business_logic_classes.Company;

import java.util.List;

/**
 * CompaniesPricesAdapter
 * Binds the data from the CompaniesPricesActivity to the elements in the recyclerview
 */


public class CompaniesPricesAdapter extends RecyclerView.Adapter<CompaniesPricesHolder>{

    //-----------------------------Variables-----------------------------
    private List<Company> companies;
    private Context context;
    private int itemResource;
    private int recentlyDeletedCompanyPosition;
    private Company recentlyDeletedCompany;

    //-----------------------------Constructor-----------------------------
    public CompaniesPricesAdapter(Context context, int itemResource, List<Company> companies)
    {
        this.companies = companies;
        this.context = context;
        this.itemResource = itemResource;
    }

    //-----------------------------Methods-----------------------------

    /**
     * onCreateViewHolder()
     * @param parent
     * @param viewType
     * @return view holder
     */
    @Override
    public CompaniesPricesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new CompaniesPricesHolder(this.context, view);
    }

    /**
     * onBindViewHolder()
     * Binds data to the view holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CompaniesPricesHolder holder, int position) {

        // Use position to access the correct portfolio object
        Company c = this.companies.get(position);

        // Bind the portfolio object to the holder
        holder.bindCompanyPrice(c);
    }

    /**
     * getItemCount()
     * @return the size of the array list
     */
    @Override
    public int getItemCount() {
        return this.companies.size();
    }

    /**
     * deleteItem()
     * deletes an item in a given position
     * @param position the position of the item to be deleted
     */
    public void deleteItem(int position) {
        recentlyDeletedCompany = companies.get(position);
        recentlyDeletedCompanyPosition = position;
        companies.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }
}
