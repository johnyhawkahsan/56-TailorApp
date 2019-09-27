package com.johnyhawkdesigns.a56_tailorapp.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

public class SizeDetailFragment extends Fragment {

    // callback method implemented by MainActivity
    public interface SizeDetailFragmentListener {
        void onPersonDeleted();

        void onEditPerson(int personID);
    }

    private SizeDetailFragmentListener listener;

    private static final String TAG = SizeDetailFragment.class.getSimpleName();

    PersonViewModel personViewModel;

    public int personID;

    private TextView textViewName;
    private TextView textViewCustID;
    private TextView tvCustName;
    private TextView tvMobileNo;
    private TextView tvMobileNoAlternate;
    private TextView tvAddress;
    private TextView tvNeck;
    private TextView tvChest;
    private TextView tvWaist;
    private TextView tvHip;
    private TextView tvPantSeat;
    private TextView tvShirtLength;
    private TextView tvShoulderWidth;
    private TextView tvArm;
    private TextView tvCoatSleeve;
    private TextView tvHandCuff;
    private TextView tvLegOpening;
    private TextView tvLastProfileUpdateDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_size_details, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            personID = arguments.getInt("personID"); // receive personID
            Log.d(TAG, "onCreateView: received personID = " + personID);
        }

        textViewName = view.findViewById(R.id.textViewName);
        textViewCustID = view.findViewById(R.id.textViewCustID);
        tvCustName = view.findViewById(R.id.tvCustName);
        tvMobileNo = view.findViewById(R.id.tvMobileNo);
        tvMobileNoAlternate = view.findViewById(R.id.tvMobileNoAlternate);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvNeck = view.findViewById(R.id.tvNeck);
        tvChest = view.findViewById(R.id.tvChest);
        tvWaist = view.findViewById(R.id.tvWaist);
        tvHip = view.findViewById(R.id.tvHip);
        tvPantSeat = view.findViewById(R.id.tvPantSeat);
        tvShirtLength = view.findViewById(R.id.tvShirtLength);
        tvShoulderWidth = view.findViewById(R.id.tvShoulderWidth);
        tvArm = view.findViewById(R.id.tvArm);
        tvCoatSleeve = view.findViewById(R.id.tvCoatSleeve);
        tvHandCuff = view.findViewById(R.id.tvHandCuff);
        tvLegOpening = view.findViewById(R.id.tvLegOpening);
        tvLastProfileUpdateDate = view.findViewById(R.id.tvLastProfileUpdateDate);

        personViewModel = new PersonViewModel(getActivity().getApplication());
        personViewModel.findPersonWithPersonID(personID);
        personViewModel.getSearchResults().observe(getActivity(), new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {

                Log.d(TAG, "onChanged: found Person with personID = " + personID +
                        ", \npersonName = " + person.getPersonName() +
                        ", \npersonMobileNo  = " + person.getMobileNo()
                );

                textViewName.setText(person.getPersonName());
                textViewCustID.setText(String.valueOf(person.getPersonID()));
                tvCustName.setText(person.getPersonName());
                tvMobileNo.setText(person.getMobileNo());
                tvMobileNoAlternate.setText(person.getMobileNoAlternate());
                tvAddress.setText(person.getPersonAddress());

                tvNeck.setText(String.valueOf(person.getPerson_neck()));
                tvChest.setText(String.valueOf(person.getPerson_chest()));
                tvWaist.setText(String.valueOf(person.getPerson_waist()));
                tvHip.setText(String.valueOf(person.getPerson_hip()));
                tvPantSeat.setText(String.valueOf(person.getPerson_pantSeat()));
                tvShirtLength.setText(String.valueOf(person.getPerson_shirtLength()));
                tvShoulderWidth.setText(String.valueOf(person.getPerson_ShoulderWidth()));
                tvArm.setText(String.valueOf(person.getPerson_arm()));
                tvCoatSleeve.setText(String.valueOf(person.getPerson_CoatSleeve()));
                tvHandCuff.setText(String.valueOf(person.getPerson_handcuff()));
                tvLegOpening.setText(String.valueOf(person.getPerson_legOpening()));
                tvLastProfileUpdateDate.setText(String.valueOf(person.getLastProfileUpdateDate()));


            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SizeDetailFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_person_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d(TAG, "onOptionsItemSelected: edit profile");

                listener.onEditPerson(personID);
                return true;

            case R.id.action_delete:

                Log.d(TAG, "onOptionsItemSelected: delete item");

                // Build alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.showMessage(getActivity(), "Delete Person with personID = " + personID );
                        personViewModel.deletePersonWithPersonID(personID);
                        listener.onPersonDeleted();
                        getFragmentManager().popBackStack();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



























