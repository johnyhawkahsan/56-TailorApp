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
import android.widget.TextView;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

import java.util.Date;

public class AddEditPersonFragment extends Fragment {

    //callback method implemented by MainActivity
    public interface AddEditFragmentListener{
        // called when personID is saved
        void onAddEditCompleted(int personID);
    }

    private AddEditFragmentListener addEditFragmentListener;

    private static final String TAG = AddEditPersonFragment.class.getSimpleName();
    private static PersonViewModel personViewModel;

    private boolean addingNewRecord = true; // adding (true) or editing (false)

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
    private TextView textInputProfileUpdateDate;
    private Button saveSize;

    Date currentDate;
    String profileUpdateDateString;

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

    private int personID = 0;

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
        textInputProfileUpdateDate = view.findViewById(R.id.textInputProfileUpdateDate);

        saveSize = view.findViewById(R.id.saveSize);
        saveSize.setOnClickListener(saveButtonClicked);



        // First we check if there are any arguments present
        if (getArguments() != null) {

            // get Bundle of arguments from launching activity/fragment
            Bundle arguments = getArguments();

            // if we are adding new , there should be no data in intent to assign
            if (arguments.get("personID") != null){
                personID = arguments.getInt("personID");
                Log.d(TAG, "onCreateView: received personID = " + personID + ", addingNewRecord = false");
                addingNewRecord = false;


            personViewModel.findPersonWithPersonID(personID);
            personViewModel.getSearchResults().observe(this, new Observer<Person>() {
                @Override
                public void onChanged(@Nullable Person person) {

                    textInputName.setText(person.getPersonName());
                    textInputMobileNo.setText(person.getMobileNo());
                    textInputMobileNoAlternate.setText(person.getMobileNoAlternate());
                    textInputAddress.setText(person.getPersonAddress());
                    textInputNeck.setText(String.valueOf(person.getPerson_neck()));
                    textInputWaist.setText(String.valueOf(person.getPerson_waist()));
                    textInputHip.setText(String.valueOf(person.getPerson_hip()));
                    textInputChest.setText(String.valueOf(person.getPerson_chest()));
                    textInputArm.setText(String.valueOf(person.getPerson_arm()));
                    textInputHandCuff.setText(String.valueOf(person.getPerson_handcuff()));
                    textInputShirtLength.setText(String.valueOf(person.getPerson_shirtLength()));
                    textInputLegOpening.setText(String.valueOf(person.getPerson_legOpening()));
                    textInputProfileUpdateDate.setText(profileUpdateDateString);

                }
            });

            }
        }


        currentDate = AppUtils.getCurrentDateTime();
        profileUpdateDateString = AppUtils.getFormattedDateString(currentDate);
        textInputProfileUpdateDate.setText(profileUpdateDateString);

        return view;
}


    // When Save button is clicked
    private final View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");

            final Person person = new Person();

            name = textInputName.getText().toString();
            mobileNo = textInputMobileNo.getText().toString();
            mobileNoAlternate = textInputMobileNoAlternate.getText().toString();
            address = textInputAddress.getText().toString();
            size_neck = textInputNeck.getText().toString();
            size_waist = textInputWaist.getText().toString();
            size_hip = textInputHip.getText().toString();
            size_chest = textInputChest.getText().toString();
            size_arm = textInputArm.getText().toString();
            size_handCuff = textInputHandCuff.getText().toString();
            size_shirtLength = textInputShirtLength.getText().toString();
            size_legOpening = textInputLegOpening.getText().toString();

            if (name.length() == 0 || mobileNo.length() == 0 || mobileNoAlternate.length() == 0 || address.length() == 0
                    || size_neck.length() == 0 || size_waist.length() == 0 || size_hip.length() == 0 || size_chest.length() == 0
                    || size_arm.length() == 0 || size_handCuff.length() == 0 || size_shirtLength.length() == 0 || size_legOpening.length() == 0){

                Log.d(TAG, "onClick: any of the parameters is empty");
                AppUtils.showMessage(getActivity(), "Please fill all details!");

            }

            //If no field is left empty and everything is filled
            else {
                person.setPersonName(name);
                person.setMobileNo(mobileNo);
                person.setMobileNoAlternate(mobileNoAlternate);
                person.setPersonAddress(address);
                person.setPerson_neck(Integer.parseInt(size_neck));
                person.setPerson_waist(Integer.parseInt(size_waist));
                person.setPerson_hip(Integer.parseInt(size_hip));
                person.setPerson_chest(Integer.parseInt(size_chest));
                person.setPerson_arm(Integer.parseInt(size_arm));
                person.setPerson_handcuff(Integer.parseInt(size_handCuff));
                person.setPerson_shirtLength(Integer.parseInt(size_shirtLength));
                person.setPerson_legOpening(Integer.parseInt(size_legOpening));
                person.setLastProfileUpdateDate(currentDate);


                // If addingNewRecord is true and we are not editing existing record
                if (addingNewRecord){
                    personViewModel.insert(person);
                    Log.d(TAG, "onClick: person named: " + name + " added to the database");
                    AppUtils.showMessage(getActivity(), "person named: " + name + " added to the database");
                    addEditFragmentListener.onAddEditCompleted(personID);
                    getFragmentManager().popBackStack(); // finish this fragment
                }

                // if addingNewRecord == false, we are editing existing record
                else {
                    Log.d(TAG, "onClick: editing existing record with personID = " + personID);
                    person.setPersonID(personID);
                    personViewModel.update(person);
                    addEditFragmentListener.onAddEditCompleted(personID);
                    getFragmentManager().popBackStack();
                }
            }

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addEditFragmentListener = (AddEditFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addEditFragmentListener = null;
    }
}
