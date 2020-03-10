package com.example.portfoliobalancer.portfolio_settings_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.portfoliobalancer.R;
import com.example.portfoliobalancer.business_logic_classes.Company;
import java.util.List;

/**
 * PortfolioSettingsAdapter
 * Binds the data from the PortfolioSettingsActivity to the elements in the recyclerview
 */

public class PortfoliosSettingsAdapter extends RecyclerView.Adapter<PortfoliosSettingsHolder>{

    public interface OnCompanyDeleted {
        void onDeleteCompanyClick (double value);
    }

    //-----------------------------Variables-----------------------------
    private List<Company> companies;
    private Context context;
    private int itemResource;
    private OnCompanyDeleted mCallback;

    //-----------------------------Constructor-----------------------------
    public PortfoliosSettingsAdapter(Context context, int itemResource, List<Company> companies, OnCompanyDeleted listener)
    {
        this.companies = companies;
        this.context = context;
        this.itemResource = itemResource;
        this.mCallback = listener;
    }

    //-----------------------------Methods-----------------------------

    /**
     * onCreateViewHolder()
     * @param parent
     * @param viewType
     * @return view holder
     */
    @Override
    public PortfoliosSettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.itemResource, parent, false);
        return new PortfoliosSettingsHolder(this.context, view);
    }

    /**
     * onBindViewHolder()
     * Binds data to the view holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final PortfoliosSettingsHolder holder, int position) {

        // Use position to access the correct company object
        Company c = this.companies.get(position);

        // Bind the company object to the holder
        holder.bindPortfolio(c);

        //-----------------------------Event Listener Methods----------------------------
        //deletes company
        holder.itemView.findViewById(R.id.entry_company_delete_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCallback.onDeleteCompanyClick(companies.get(holder.getAdapterPosition()).getCurrentUnitPrice());
                companies.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), companies.size());

            }
        });
    }

    /**
     * getItemCount()
     * @return the size of the array list
     */
    @Override
    public int getItemCount() {
        return this.companies.size();
    }
}
