package com.johnyhawkdesigns.a56_tailorapp.fragment;

import android.app.SearchManager;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.adapter.PersonListAdapter;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

import java.util.List;


public class SizeListFragment extends Fragment {

    // callback method implemented by MainActivity
    public interface SizeListFragmentListener{
        void onPersonSelected(int personID);
    }
    // used to inform the MainActivity when an item is selected to redirect us to DetailActivity
    private SizeListFragmentListener listener;


    private static final String TAG = SizeListFragment.class.getSimpleName();

    private TextView emptyTextView;
    private ImageView emptyImageView;

    private RecyclerView recyclerView;
    private PersonListAdapter personListAdapter;

    private PersonViewModel personViewModel;
    FloatingActionButton floatingActionButton;

    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);// Inflate the layout for this fragment


        recyclerView = view.findViewById(R.id.recyclerViewSizeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Accessing items from <include layout="@layout/empty_data"/> empty_data layout
        emptyTextView = view.findViewById(R.id.tv__empty);
        emptyImageView = view.findViewById(R.id.img__empty);




        // setup for searchView functionality
        searchView = (SearchView) view.findViewById(R.id.searchSize);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE); // Associate searchable configuration with the SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        personViewModel = new PersonViewModel(getActivity().getApplication());
        
        personListAdapter = new PersonListAdapter(getActivity(), new PersonListAdapter.RecyclerViewItemClickListener() {
            @Override
            public void onClick(int personID) {
                Log.d(TAG, "onClick: received personID = " + personID);
                listener.onPersonSelected(personID);
            }
        });

        recyclerView.setAdapter(personListAdapter);

        // get all record from our viewModel to display inside RecyclerView
        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable final List<Person> people) {
                Log.d(TAG, "onChanged: personList size = " + people.size());

                // Loop through all returned list items and display in logs
                for (int i = 0; i < people.size(); i++){
                    Log.d(TAG, "personID = " + people.get(i).getPersonID());
                }

                personListAdapter.setPersonList(people); // set list inside our adapter to this returned list of persons

                if (people.size() > 0 ){
                    emptyTextView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                }else {
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                }

                // ---------------------SearchView functional code-----------------------------///
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // filter recycler view when query submitted
                        personListAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        // filter recycler view when text is changed
                        personListAdapter.getFilter().filter(query);
                        return false;
                    }
                });



            }
        });


        //When Floating button is clicked, we need to be redirected to AddEditPersonFragment
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNewSize);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddEditPersonFragment
                Log.d(TAG, "Launching AddEditPersonFragment");
                Toast.makeText(getActivity(), "Add New Person", Toast.LENGTH_SHORT).show();

                AddEditPersonFragment addEditPersonFragment = new AddEditPersonFragment();
                //Bundle args = new Bundle();
                //args.putInt("id", 12345);
                //addEditPersonFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, addEditPersonFragment);
                transaction.addToBackStack(null); // By using this line, we will be able to return to this SizeListFragment after adding a new record in AddEditPersonFragment
                transaction.commit();
            }
        });




        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        listener = (SizeListFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        listener = null;
    }


}
