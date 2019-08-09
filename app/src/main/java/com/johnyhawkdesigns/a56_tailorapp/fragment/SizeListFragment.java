package com.johnyhawkdesigns.a56_tailorapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.adapter.PersonListAdapter;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;


public class SizeListFragment extends Fragment {

    private static final String TAG = SizeListFragment.class.getSimpleName();

    private TextView emptyTextView;
    private RecyclerView recyclerView;
    private PersonListAdapter personListAdapter;

    private PersonViewModel personViewModel;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_size, container, false);// Inflate the layout for this fragment


        recyclerView = view.findViewById(R.id.recyclerViewSizeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        personViewModel = new PersonViewModel(getActivity().getApplication());
        
        personListAdapter = new PersonListAdapter(getActivity(), new PersonListAdapter.RecyclerViewItemClickListener() {
            @Override
            public void onClick(int mobileNo) {
                Log.d(TAG, "onClick: received mobileNo = " + mobileNo);
            }
        });

        recyclerView.setAdapter(personListAdapter);

        // Temporary data for our list
        List<Person> personList = new ArrayList<>();
        Person samplePerson = new Person();
        //samplePerson.setMobileNo(03449085371L);
        personList.add(samplePerson);

        //When Floating button is clicked, we need to be redirected to AddEditPersonFragment
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNewSize);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch AddEditPersonFragment
                Log.d(TAG, "onClick: ");
                Toast.makeText(getActivity(), "Add New Person", Toast.LENGTH_SHORT).show();

                AddEditPersonFragment addEditPersonFragment = new AddEditPersonFragment();
                //Bundle args = new Bundle();
                //args.putInt("id", 12345);
                //addEditPersonFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, addEditPersonFragment);
                transaction.commit();
            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
