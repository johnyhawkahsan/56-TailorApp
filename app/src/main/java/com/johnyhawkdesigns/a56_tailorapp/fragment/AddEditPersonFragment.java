package com.johnyhawkdesigns.a56_tailorapp.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

public class AddEditPersonFragment extends Fragment {

    private static final String TAG = AddEditPersonFragment.class.getSimpleName();

    private static PersonViewModel personViewModel;

    private boolean addingNewRecord = true; // adding (true) or editing (false)
    private int mobileNoReceived = 0;

    private TextInputEditText textInputName;
    private TextInputEditText textInputMobileNo;
    private TextInputEditText textInputMobileNoAlternate;
    private TextInputEditText textInputAddress;
    private TextInputEditText textInputNeck;
    private TextInputEditText textInputWaist;
    private TextInputEditText textInputHip;
    private TextInputEditText textInputChest;
    private TextInputEditText textInputArm;
    private TextInputEditText textInputHandCuff;
    private TextInputEditText textInputShirtLength;
    private TextInputEditText textInputLegOpening;
    private Button saveSize;

    String name = "";
    String mobileNo = "";
    String mobileNoAlternate = "";
    String address = "";
    String size_neck = "";
    String size_waist = "";
    String size_hip = "";
    String size_chest = "";
    String size_arm = "";
    String size_handCuff = "";
    String size_shirtLength = "";
    String size_legOpening = "";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_add_edit_size, container, false);

        personViewModel = new PersonViewModel(getActivity().getApplication());

        textInputName = view.findViewById(R.id.textInputName);
        textInputMobileNo = view.findViewById(R.id.textInputMobileNo);
        textInputMobileNoAlternate = view.findViewById(R.id.textInputMobileNoAlternate);
        textInputAddress = view.findViewById(R.id.textInputAddress);
        textInputNeck = view.findViewById(R.id.textInputNeck);
        textInputWaist = view.findViewById(R.id.textInputWaist);
        textInputHip = view.findViewById(R.id.textInputHip);
        textInputChest = view.findViewById(R.id.textInputChest);
        textInputArm = view.findViewById(R.id.textInputArm);
        textInputHandCuff = view.findViewById(R.id.textInputHandCuff);
        textInputShirtLength = view.findViewById(R.id.textInputShirtLength);
        textInputLegOpening = view.findViewById(R.id.textInputLegOpening);


        saveSize = view.findViewById(R.id.saveSize);
        saveSize.setOnClickListener(saveButtonClicked);

        // get Bundle of arguments from launching activity/fragment
        Bundle arguments = getArguments();
        mobileNoReceived = arguments.getInt("mobileNo");


        // if we are adding new , there should be no data in intent to assign
        if (arguments.get("medID") != null) {
            mobileNoReceived =  arguments.getInt("chID");
            Log.d(TAG, "onCreateView: received mobileNo = " + mobileNo);
            addingNewRecord = false;

            /*
            personViewModel.findPersonWithMobileNo(mobileNoReceived);
            personViewModel.getSearchResults().observe(this, new Observer<Person>() {
                @Override
                public void onChanged(@Nullable Person person) {

                }
            });
             */


        }



        return view;
}


    // When Save button is clicked
    private final View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            AppUtils.showMessage(getActivity(), "Save this size!");

            Person person = new Person();
            name = textInputName.getText().toString();


            //personViewModel.insert(personsss);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
