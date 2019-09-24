package com.johnyhawkdesigns.a56_tailorapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> implements Filterable{


    private static final String TAG = PersonListAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<Person> mPersonsList;
    private List<Person> mPersonsListFiltered; // Returns filtered search items


    // interface implemented by MainActivity to respond when the user touches an item in the RecyclerView.
    public interface RecyclerViewItemClickListener {
        void onClick(int personID);
    }

    private final RecyclerViewItemClickListener recyclerViewItemClickListener;

    //Constructor
    public PersonListAdapter(Context context, RecyclerViewItemClickListener recyclerViewItemClickListener){
        mContext = context;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
        mInflator = LayoutInflater.from(context);
    }

    // Instead of using personList in a constructor, we use it here so we can use in MainActivity's observer.
    public void setPersonList(List<Person> personList){
        this.mPersonsList = personList; // We should not remove this line as well, because removing it will remove complete list once an item is searched.
        this.mPersonsListFiltered = personList; // instead of using mPersonsList, now we use mPersonsListFiltered
        notifyDataSetChanged();
    }

    //PersonViewHolder class
    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView customerID, customerName, customerMobileNo, customerMobileNoAlternate, customerAddress;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            customerID = itemView.findViewById(R.id.customerID);
            customerName = itemView.findViewById(R.id.customerName);
            customerMobileNo = itemView.findViewById(R.id.customerMobileNo);
            customerMobileNoAlternate = itemView.findViewById(R.id.customerMobileNoAlternate);
            customerAddress = itemView.findViewById(R.id.customerAddress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Person person = mPersonsList.get(getAdapterPosition());// Get item at this specific position from list
            int personID = person.getPersonID();
            String name = person.getPersonName();
            AppUtils.showMessage(mContext, "Clicked on Person with name = " + name + ", personID = " + personID);
            Log.d(TAG, "onClick: Clicked on Person with name = " + name + ", personID = " + personID);

            // This interface method sends mobileNo to MainActivity's PersonListAdapter constructor method.
            recyclerViewItemClickListener.onClick(personID);
        }
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.recycler_item_size, viewGroup, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        Person currentPerson = mPersonsListFiltered.get(position); // instead of using mPersonsList, now I'm using mPersonsListFiltered to return search results
        personViewHolder.customerID.setText("PersonID = " + String.valueOf(currentPerson.getPersonID()));
        personViewHolder.customerName.setText(currentPerson.getPersonName());
        personViewHolder.customerMobileNo.setText(currentPerson.getMobileNo());
        personViewHolder.customerMobileNoAlternate.setText(currentPerson.getMobileNoAlternate());
        personViewHolder.customerAddress.setText(currentPerson.getPersonAddress());

        Log.d(TAG, "onBindViewHolder: currentPerson.getPersonID() = " + currentPerson.getPersonID() +
                ", \ncurrentPerson.getPersonName() = " + currentPerson.getPersonName());
    }

    // getItemCount() is called many times, and when it is first called, mPersonsList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {

        if (mPersonsListFiltered == null)
            return 0;
        else
            return  mPersonsListFiltered.size();

    }


    // -----------------------Code to implement search functionality--------------------------- //
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d(TAG, "performFiltering: charString = " + charString);

                if (charString.isEmpty()) {
                    mPersonsListFiltered = mPersonsList; // if search is empty, then filteredList contains complete list
                }
                // if search is not empty, then filtered list should be items that have been typed in search box
                else {
                    List<Person> filteredList = new ArrayList<>();
                    for (Person row : mPersonsList) { // iterate through all the list

                        // name match condition. this might differ depending on your requirement here we are looking for name or phone number match
                        if (row.getPersonName().toLowerCase().contains(charString.toLowerCase()) || row.getMobileNo().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mPersonsListFiltered = filteredList; // now our mPersonsListFiltered has new data
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPersonsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPersonsListFiltered = (ArrayList<Person>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}
