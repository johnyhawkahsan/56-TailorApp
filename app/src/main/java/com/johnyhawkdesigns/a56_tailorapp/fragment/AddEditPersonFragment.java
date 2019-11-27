package com.johnyhawkdesigns.a56_tailorapp.fragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.johnyhawkdesigns.a56_tailorapp.R;
import com.johnyhawkdesigns.a56_tailorapp.other.AppUtils;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.model.Person;
import com.johnyhawkdesigns.a56_tailorapp.roomdatabase.viewModel.PersonViewModel;

import java.io.IOException;
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

    private static final int PICKFILE_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    private boolean addingNewRecord = true; // adding (true) or editing (false)

    public Uri selectedImageUri;
    public Bitmap selectedImageBitmap;
    byte[] bitmapByteArray = null;

    private TextInputEditText textInputName;
    private TextInputEditText textInputMobileNo;
    private TextInputEditText textInputMobileNoAlternate;
    private TextInputEditText textInputAddress;

    private TextInputEditText textInputNeck;
    private TextInputEditText textInputChest;
    private TextInputEditText textInputWaist;
    private TextInputEditText textInputHip;
    private TextInputEditText textInputPantSeat;
    private TextInputEditText textInputShirtLength;
    private TextInputEditText textInputShoulderWidth;
    private TextInputEditText textInputArm;
    private TextInputEditText textInputCoatSleeve;
    private TextInputEditText textInputHandCuff;
    private TextInputEditText textInputLegOpening;
    private TextView textInputProfileUpdateDate;

    private ImageView addCustomerImage;

    private Button saveSize;



    Date currentDate;
    String profileUpdateDateString;

    String name = "";
    String mobileNo = "";
    String mobileNoAlternate = "";
    String address = "";
    String size_neck = "";
    String size_chest = "";
    String size_waist = "";
    String size_hip = "";
    String size_pantSeat = "";
    String size_shirtLength = "";
    String size_shoulderWidth = "";
    String size_arm = "";
    String size_coatSleeve = "";
    String size_handCuff = "";
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
        textInputChest = view.findViewById(R.id.textInputChest);
        textInputWaist = view.findViewById(R.id.textInputWaist);
        textInputHip = view.findViewById(R.id.textInputHip);
        textInputPantSeat = view.findViewById(R.id.textInputPantSeat);
        textInputShirtLength = view.findViewById(R.id.textInputShirtLength);
        textInputShoulderWidth = view.findViewById(R.id.textInputShoulderWidth);
        textInputArm = view.findViewById(R.id.textInputArm);
        textInputCoatSleeve = view.findViewById(R.id.textInputCoatSleeve);
        textInputHandCuff = view.findViewById(R.id.textInputHandCuff);
        textInputLegOpening = view.findViewById(R.id.textInputLegOpening);
        textInputProfileUpdateDate = view.findViewById(R.id.textInputProfileUpdateDate);
        addCustomerImage = view.findViewById(R.id.addCustomerImage);
        saveSize = view.findViewById(R.id.saveSize);
        saveSize.setOnClickListener(saveButtonClicked);


        selectedImageUri = null;
        selectedImageBitmap = null;

        // First we check if there are any arguments present
        if (getArguments() != null) {

            // get Bundle of arguments from launching activity/fragment
            Bundle arguments = getArguments();

            // if we are adding new person, there should be no data in intent to assign
            if (arguments.get("personID") != null){
                personID = arguments.getInt("personID");
                Log.d(TAG, "onCreateView: received personID = " + personID + ", addingNewRecord = false");
                addingNewRecord = false;

            // Now we need to search our database for that personID and return results and pre-populate our editText
            personViewModel.findPersonWithPersonID(personID);
            personViewModel.getSearchResults().observe(this, new Observer<Person>() {
                @Override
                public void onChanged(@Nullable Person person) {

                    textInputName.setText(person.getPersonName());
                    textInputMobileNo.setText(person.getMobileNo());
                    textInputMobileNoAlternate.setText(person.getMobileNoAlternate());
                    textInputAddress.setText(person.getPersonAddress());
                    textInputNeck.setText(String.valueOf(person.getPerson_neck()));
                    textInputChest.setText(String.valueOf(person.getPerson_chest()));
                    textInputWaist.setText(String.valueOf(person.getPerson_waist()));
                    textInputHip.setText(String.valueOf(person.getPerson_hip()));
                    textInputPantSeat.setText(String.valueOf(person.getPerson_pantSeat()));
                    textInputShirtLength.setText(String.valueOf(person.getPerson_shirtLength()));
                    textInputShoulderWidth.setText(String.valueOf(person.getPerson_ShoulderWidth()));
                    textInputArm.setText(String.valueOf(person.getPerson_arm()));
                    textInputCoatSleeve.setText(String.valueOf(person.getPerson_CoatSleeve()));
                    textInputHandCuff.setText(String.valueOf(person.getPerson_handcuff()));
                    textInputLegOpening.setText(String.valueOf(person.getPerson_legOpening()));
                    textInputProfileUpdateDate.setText(profileUpdateDateString);

                    // If editing profile person has already image provided, we need to take out this image and assign to our variable selectedImageBitmap
                    if (person.getPersonImage() != null){
                        Log.d(TAG, "onChanged: person has image, now load into ImageView");
                        selectedImageBitmap = AppUtils.getBitmapFromByteArray(person.getPersonImage());
                        Glide
                                .with(getActivity())
                                .load(selectedImageBitmap)
                                .apply(RequestOptions.circleCropTransform())
                                .into(addCustomerImage);
                    }

                }
            });

            }
        }

        addCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Open popup menu to choose new photo");

                PopupMenu popupMenu = new PopupMenu(getActivity(), addCustomerImage);
                popupMenu.getMenuInflater().inflate(R.menu.menu_image, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_select_image:
                                Log.d(TAG, "onMenuItemClick: ");

                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//Allow the user to select a particular kind of data and return it. This is different than ACTION_PICK in that here we just say what kind of data is desired, not a URI of existing data from which the user can pick.
                                intent.setType("image/*");
                                //startActivityForResult(intent, PICKFILE_REQUEST_CODE); // Both startActivityForResult do the same
                                startActivityForResult(Intent.createChooser(intent, "Select a picture"), PICKFILE_REQUEST_CODE);

                                return true;

                            default:
                                return true;
                        }
                    }
                });

                popupMenu.show(); //showing popup menu
            }
        });

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
            size_chest = textInputChest.getText().toString();
            size_waist = textInputWaist.getText().toString();
            size_hip = textInputHip.getText().toString();
            size_pantSeat = textInputPantSeat.getText().toString();
            size_shirtLength = textInputShirtLength.getText().toString();
            size_shoulderWidth = textInputShoulderWidth.getText().toString();
            size_arm = textInputArm.getText().toString();
            size_coatSleeve = textInputCoatSleeve.getText().toString();
            size_handCuff = textInputHandCuff.getText().toString();
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
                person.setPerson_chest(Integer.parseInt(size_chest));
                person.setPerson_waist(Integer.parseInt(size_waist));
                person.setPerson_hip(Integer.parseInt(size_hip));
                person.setPerson_pantSeat(Integer.parseInt(size_pantSeat));
                person.setPerson_shirtLength(Integer.parseInt(size_shirtLength));
                person.setPerson_ShoulderWidth(Integer.parseInt(size_shoulderWidth));
                person.setPerson_arm(Integer.parseInt(size_arm));
                person.setPerson_CoatSleeve(Integer.parseInt(size_coatSleeve));
                person.setPerson_handcuff(Integer.parseInt(size_handCuff));
                person.setPerson_legOpening(Integer.parseInt(size_legOpening));
                person.setLastProfileUpdateDate(currentDate);

                // In case of editing old record, we need to check if user already had image stored
                if (selectedImageBitmap != null){
                    person.setPersonImage(AppUtils.getBytesFromBitmap(selectedImageBitmap, 100));
                }


                // In case of adding new record, we need to check returned uri
                if (selectedImageUri != null){
                    Log.d(TAG, "doInBackground: Kilobytes before compression: " + selectedImageBitmap.getByteCount());
                    Log.d(TAG, "doInBackground: megabytes before compression: " + selectedImageBitmap.getByteCount() / 1000000);//To get image size before compression, dividing by 1000000 is size of mega bytes

                    bitmapByteArray = AppUtils.getBytesFromBitmap(selectedImageBitmap, 100); // translate selected bitmap into byteArray[]

                    Log.d(TAG, "doInBackground: megabytes after compression: " + bitmapByteArray.length / 1000000);//To get image size after compression
                    Log.d(TAG, "doInBackground: Kilobytes after compression: " + bitmapByteArray.length);


                    person.setPersonImage(bitmapByteArray); // store this image byte array to our database table
                }



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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting a new image from memory
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            try{
                // translate this data into imageUri
                selectedImageUri = data.getData();
                // now we need to convert this image uri into a bitmap
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
            } catch (IOException e){
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }

            if (selectedImageUri != null){
                Log.d(TAG, "onActivityResult: image uri: " + selectedImageUri);

                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())){
                    // if permission is granted, below lines will get executed.
                    Glide
                            .with(getActivity())
                            .load(selectedImageUri)
                            .apply(RequestOptions.circleCropTransform())
                            .into(addCustomerImage);

                }

            }

        }
    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT; // Get current sdk version
        Log.d(TAG, "checkPermissionREAD_EXTERNAL_STORAGE: currentAPIVersion = " + currentAPIVersion);

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) { // android M api is 23

            Log.d(TAG, "checkPermissionREAD_EXTERNAL_STORAGE: APP DOES NOT YET HAVE PERMISSIONS");

            // checks if the app does not have permission needed
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // shows an explanation of why permission is needed
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            // if app already has permission to write to external storage
            Log.d(TAG, "checkPermissionREAD_EXTERNAL_STORAGE: APP ALREADY HAS PERMISSIONS");
            return true;
        }
    }

    // Show dialog for permissions
    public void showDialog(final String msg, final Context context, final String permission) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    // called by the system when the user either grants or denies the permission for saving an image
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // switch chooses appropriate action based on which feature requested permission
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: PackageManager.PERMISSION_GRANTED");
                    // when user first time grants the permissions, these lines get executed
                    Glide
                            .with(getActivity())
                            .load(selectedImageUri)
                            .apply(RequestOptions.circleCropTransform())
                            .into(addCustomerImage);

                } else {
                    Toast.makeText(getActivity(), "Get read permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

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
