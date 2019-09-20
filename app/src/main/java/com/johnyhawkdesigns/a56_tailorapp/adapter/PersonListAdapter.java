package com.johnyhawkdesigns.a56_tailorapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>{


    private static final String TAG = PersonListAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<Person> mPersons;

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
        this.mPersons = personList;
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
            Person person = mPersons.get(getAdapterPosition());// Get item at this specific position from list
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
        Person currentPerson = mPersons.get(position);
        personViewHolder.customerID.setText("PersonID = " + String.valueOf(currentPerson.getPersonID()));
        personViewHolder.customerName.setText(currentPerson.getPersonName());
        personViewHolder.customerMobileNo.setText(currentPerson.getMobileNo());
        personViewHolder.customerMobileNoAlternate.setText(currentPerson.getMobileNoAlternate());
        personViewHolder.customerAddress.setText(currentPerson.getPersonAddress());

        Log.d(TAG, "onBindViewHolder: currentPerson.getPersonID() = " + currentPerson.getPersonID() +
                ", \ncurrentPerson.getPersonName() = " + currentPerson.getPersonName());
    }

    // getItemCount() is called many times, and when it is first called, mPersons has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {

        if (mPersons == null)
            return 0;
        else
            return  mPersons.size();

    }

}
